package emergencyDepartment;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CalculateTriage calculateTriage = new CalculateTriage("diseases.txt");
        WeibullDistribution Weibull = new WeibullDistribution();
        QueueManager Queue = new QueueManager();
        BedManager bedManager = new BedManager();
        PhysicianManager physicianManager = new PhysicianManager();

       //----------Clock-------Managment----------------////////////
        
        GlobalClock globalClock = new GlobalClock();

        // Create the clock thread that will update the global time
        Thread clockThread = new Thread(new ClockThread(globalClock));
        clockThread.start();
        
        
        
        /////////////////////////////////////////////////////////
        int start_time = 0;
        int end_time = 1000;
        int current_time = 0;
        int patient_count =1;
        
        while (globalClock.getCurrentTime() < end_time) {
            //System.out.println("/////////-------------" + globalClock.getCurrentTime() + "----------------///////////////////////");
            
            int triage = calculateTriage.triageCal();
            int arrival_gap = Weibull.generateArrivalTime();
            int arrival = (int) (arrival_gap + globalClock.getCurrentTime());
            while(globalClock.getCurrentTime()<arrival) {
            	
            }
            // Create a new patient with the current patient_count before incrementing
            Patient newpatient = new Patient(patient_count, triage, arrival);
            patient_count = patient_count + 1; // Increment after patient creation
            
            Bed allocatedBed = bedManager.allocateBed(triage);
            
            current_time = current_time + arrival_gap;
            System.out.println("Patient ID: " + newpatient.getId() + "\t Triage Category: " + newpatient.getTriageCategory() + 
                "   Arrival Gap: " + arrival_gap + "\tArrival Time: " + newpatient.getArrivalTime() + 
                "\tCurrent Time: " + globalClock.getCurrentTime() + "\tTreatment Time: " + newpatient.treatmentTime + 
                "\tDischarge Time: " + newpatient.dischargeTime );

            if (allocatedBed != null) {
                allocatedBed.setAvailable(false);
                newpatient.bedAssignmnetTime = (int) globalClock.getCurrentTime();
                newpatient.setBedAssigned(true);
                System.out.println("Allocated " + allocatedBed.getType() + " with ID: " + allocatedBed.getId());

                // Physician allocation logic
                Physician allocatedPhysician = physicianManager.allocatePhysician(triage);
                if (allocatedPhysician != null) {
                    physicianManager.physicians.get(allocatedPhysician.getId()).addOnePatient();
                    newpatient.setPhysicianAssigned(true);
                    newpatient.setPhysician(allocatedPhysician);
                    System.out.println("Allocated " + allocatedPhysician.getType() + " with ID: " + allocatedPhysician.getId()+"\n----------------------------------------------------------------\n");
                } else {
                    System.out.println("No available physician for Category " + triage + " patient."+"\n----------------------------------------------------------------\n");
                }
            } else {
                Queue.addPatient(newpatient);
                System.out.println("No available bed for Category " + triage + " patient."+"\n----------------------------------------------------------------\n");
            }
        }

        ///////////////////////////////////////////////////////////Experimentations/////////////////////
        int BedNmber = bedManager.getBedsnumber();
        int availableBedsCount = 0;
        for(Bed bed : bedManager.getBeds()) {
        	if(bed.isAvailable()) {
        		availableBedsCount = availableBedsCount + 1;
        	}
        }
        
        int PhysiciansNumber = physicianManager.getPhysiciansnumber();
        int availablePhysiciansCount = 0;
        int maxAssignmnets=0;
        for(Physician physician : physicianManager.getPhysicians()) {
        	if(physician.getMaxPatients()-physician.getCurrentPatients()>0) {
        		availablePhysiciansCount = availablePhysiciansCount + (physician.getMaxPatients()-physician.getCurrentPatients());
        	}
        	maxAssignmnets = maxAssignmnets + physician.getMaxPatients();
        }
        
        System.out.println("\n\nQueue Size:" + Queue.getQueueSize());
        System.out.println("Bed No: "+ BedNmber + "\t\t Availabe Beds: " + availableBedsCount);
        System.out.println("Physicans No: "+ PhysiciansNumber + "\t Available Assignmnets of Physicans: " + availablePhysiciansCount +"\t\tMax Assignmnets: " + maxAssignmnets);
        clockThread.interrupt();  // Interrupt the clock thread

        clockThread.join();  // Wait for the thread to terminate
        System.out.println("Clock thread has been stopped. Exiting program.");
        
    }
}
