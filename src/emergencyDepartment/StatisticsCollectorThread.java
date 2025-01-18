package emergencyDepartment;

import java.util.*;

public class StatisticsCollectorThread implements Runnable {
    private GlobalClock globalClock;
    private QueueManager queueManager;
    private BedManager bedManager;
    private List<Physician> physicians;
    private List<Bed> beds;

    private List<Integer> queueSizes;  // To store queue size data
    private List<Double> physicianUtilizations;  // To store physician utilization data
    private List<Double> bedUtilizations;  // To store bed utilization data
    private int timeUnitCounter;

    public StatisticsCollectorThread(GlobalClock globalClock, QueueManager queueManager, List<Physician> physicians, List<Bed> beds, BedManager bedManager) {
        this.globalClock = globalClock;
        this.queueManager = queueManager;
        this.physicians = physicians;
        this.beds = beds;
        this.bedManager = bedManager;

        this.queueSizes = new ArrayList<>();
        this.physicianUtilizations = new ArrayList<>();
        this.bedUtilizations = new ArrayList<>();
        this.timeUnitCounter = 0;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(100); 

                // Increment time unit counter
                timeUnitCounter++;

                // Collect the current statistics
                int queueSize = queueManager.getQueueSize();
                double physicianUtilization = calculatePhysicianUtilization();
                double bedUtilization = calculateBedUtilization();

                // Store the collected data
                queueSizes.add(queueSize);
                physicianUtilizations.add(physicianUtilization);
                bedUtilizations.add(bedUtilization);

//                // Print statistics every 10 time units
//                if (timeUnitCounter % 10 == 0) {
//                    // Print the statistics every 10 time units
//                    System.out.println("Time Unit: " + timeUnitCounter);
//
//                    // Print the Queue Sizes
//                    System.out.println("Queue Sizes:");
//                    System.out.println("Time: " + globalClock.getCurrentTime() + " | Queue Size: " + queueSize);
//
//                    // Print the Physician Utilizations
//                    System.out.println("Physician Utilizations:");
//                    System.out.println("Time: " + globalClock.getCurrentTime() + " | Physician Utilization: " + physicianUtilizations.get(timeUnitCounter / 10 - 1) + "%");
//
//                    // Print the Bed Utilizations
//                    System.out.println("Bed Utilizations:");
//                    System.out.println("Time: " + globalClock.getCurrentTime() + " | Bed Utilization: " + bedUtilizations.get(timeUnitCounter / 10 - 1) + "%");
//                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Preserve the interrupt status
            System.out.println("Statistics collection thread interrupted.");
        }
    }


    private double calculatePhysicianUtilization() {
        double utilized = 0;
        double max = 0;
        for (Physician physician : physicians) {
            utilized += physician.getCurrentPatients();
            max += physician.getMaxPatients();  
        }
        return (utilized / max) * 100; 
    }

    // Calculate the bed utilization as a percentage (0-100)
    private double calculateBedUtilization() {
        double utilized = 0;
        for (Bed bed : beds) {
            if (!bed.isAvailable()) {
                utilized += 1;
            }
        }
        return (utilized / beds.size()) * 100;  
    }

    public List<Integer> getQueueSizes() {
        return queueSizes;
    }

    public List<Double> getPhysicianUtilizations() {
        return physicianUtilizations;
    }

    public List<Double> getBedUtilizations() {
        return bedUtilizations;
    }
}
