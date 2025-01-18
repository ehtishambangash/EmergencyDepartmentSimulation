package emergencyDepartment;

public class DeallocationThread implements Runnable {
    private GlobalClock globalClock;
    private BedManager bedManager;
    private PhysicianManager physicianManager;
    private QueueManager queueManager;

    public DeallocationThread(GlobalClock globalClock, BedManager bedManager, PhysicianManager physicianManager, QueueManager queueManager) {
        this.globalClock = globalClock;
        this.bedManager = bedManager;
        this.physicianManager = physicianManager;
        this.queueManager = queueManager;
    }

    @Override
    public void run() {
        System.out.println("Deallocation thread started. Running in parallel..."); // Signal that the thread is running
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(1); 

                //System.out.println("Deallocation thread is checking for discharges...");

                // Check for patients to discharge
                for (Bed bed : bedManager.getBeds()) {
                    if (!bed.isAvailable()) {
                        // Check if the patient assigned to this bed is due for discharge
                        Patient patient = bed.getPatient(); // Get the assigned patient
                        if (patient != null && globalClock.getCurrentTime() >= patient.getDischargeTime()) {
                            // Deallocate the bed and physician
                            bedManager.deallocateBed(bed);
                            Physician physician = patient.getAllocatedPhysician();
                            if (physician != null) {
                                physician.removePatient();
                            }

                            // Update patient attributes
                            patient.setBedAssigned(false);
                            patient.setPhysicianAssigned(false);
                            patient.setBed(null);
                            patient.setPhysician(null);
                            bed.assignPatient(null); // Clear the patient from the bed

                            System.out.println("\033[0;31m"+"Patient ID: " + patient.getId() + " has been discharged from Bed ID: " + bed.getId()+"\033[0m"+"\n----------------------------------------------------------------");

                            if (!queueManager.isQueueEmpty()) {
                                Patient nextPatient = queueManager.removePatient(); // Get the next patient from the queue
                                
                                Bed allocatedBed = bedManager.allocateBed(nextPatient.getTriageCategory());
                                if (allocatedBed != null) {
                                    allocatedBed.setAvailable(false);
                                    allocatedBed.firstAssign();
                                    allocatedBed.assignPatient(nextPatient); // Assign the patient to the bed
                                    nextPatient.setBedAssigned(true);
                                    nextPatient.bedAssignmnetTime = (int) globalClock.getCurrentTime();
                             
                                    
                                    int[] maxAllowedTime = {0, 10, 30, 60, 120};
                                    int triageCategory = nextPatient.getTriageCategory();
                                    
                                    if (triageCategory >= 1 && triageCategory <= 5 && 
                                    	    (nextPatient.bedAssignmnetTime - nextPatient.getArrivalTime() > maxAllowedTime[triageCategory - 1])) {
                                    	    
                                    	    Main.IncrementNotRecommended();
                                    	}
                                    System.out.println("\033[0;32m"+"Allocated Bed ID: " + allocatedBed.getId() + " to Patient ID: " + nextPatient.getId()+ "\tat Time: " + nextPatient.bedAssignmnetTime+"\033[0m");

                                    // Allocate a physician for the new patient
                                    Physician allocatedPhysician = physicianManager.allocatePhysician(nextPatient.getTriageCategory());
                                    if (allocatedPhysician != null) {
                                        allocatedPhysician.addPatient();
                                        nextPatient.setPhysician(allocatedPhysician);
                                        nextPatient.setPhysicianAssigned(true);
                                        System.out.println("Allocated Physician ID: " + allocatedPhysician.getId() + " to Patient ID: " + nextPatient.getId());
                                    } else {
                                        System.out.println("No available physician for Patient ID: " + nextPatient.getId());
                                    }
                                } else {
                                    System.out.println("No available bed for Patient ID: " + nextPatient.getId());
                                }
                            }
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Preserve the interrupt status
            System.out.println("Deallocation thread interrupted. Stopping the deallocation checks."+ globalClock.getCurrentTime());
        }
    }
}