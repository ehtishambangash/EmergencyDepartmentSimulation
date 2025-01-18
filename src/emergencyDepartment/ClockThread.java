package emergencyDepartment;

public class ClockThread implements Runnable {
    private GlobalClock globalClock;

    public ClockThread(GlobalClock globalClock) {
        this.globalClock = globalClock;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) { 
                Thread.sleep(10);  
                globalClock.incrementTime(1);
                //System.out.println("Clock Time - Current Time: " + globalClock.getCurrentTime());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Preserve the interrupt status
            System.out.println("Clock thread interrupted. Stopping the clock.");
        }
    }
}
