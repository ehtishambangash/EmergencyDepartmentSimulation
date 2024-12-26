package emergencyDepartment;

public class GlobalClock {
    private int currentTime = 0;

    // Synchronized method to increment time safely
    public synchronized void incrementTime(long timeToAdd) {
        currentTime += timeToAdd;
    }

    // Synchronized method to get the current time
    public synchronized int getCurrentTime() {
        return currentTime;
    }

    // Method to reset the clock
    public synchronized void resetClock() {
        currentTime = 0;
    }
}