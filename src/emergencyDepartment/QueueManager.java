package emergencyDepartment;

import java.util.*;

class QueueManager {
    private  PriorityQueue<Patient> queue;

    public QueueManager() {
        this.queue = new PriorityQueue<>(new Comparator<Patient>() {
            @Override
            public int compare(Patient p1, Patient p2) {
                // Compare based on triage level first
                if (p1.getTriageCategory() != p2.getTriageCategory()) {
                    return Integer.compare(p1.getTriageCategory(), p2.getTriageCategory());
                }
                // If triage levels are the same, compare based on arrival time
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
        });
    }

    public void addPatient(Patient patient) {
        queue.add(patient);
    }

    public Patient removePatient() {
        return queue.poll(); 
    }

    public Patient getNextPatient() {
        return queue.peek(); 
    }

    public int getQueueSize() {
        return queue.size();
    }

    public boolean isQueueEmpty() {
        return queue.isEmpty();
    }
    public PriorityQueue<Patient> getQueue() {
        return queue;
    }
}

