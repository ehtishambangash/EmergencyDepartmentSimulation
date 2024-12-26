package emergencyDepartment;

import java.util.*;

class QueueManager {
    private PriorityQueue<Patient> queue;

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

    // Add a new patient to the queue and automatically sort
    public void addPatient(Patient patient) {
        queue.add(patient);
    }

    // Remove and return the next patient based on triage level and arrival time
    public Patient removePatient() {
        return queue.poll();  // Removes and returns the highest priority patient
    }

    public Patient getNextPatient() {
        return queue.peek();  // Returns the highest priority patient without removal
    }

    // Check the current size of the queue
    public int getQueueSize() {
        return queue.size();
    }

    // Check if the queue is empty
    public boolean isQueueEmpty() {
        return queue.isEmpty();
    }
    public PriorityQueue<Patient> getQueue() {
        return queue;
    }
}

