package emergencyDepartment;

public abstract class Physician {
    private int id;  
    private String type;      // Type of physician
    private int maxPatients;  // Maximum number of patients a physician can handle simultaneously
    private int currentPatients; // Current number of patients being treated

    public Physician(int id, String type, int maxPatients) {
        this.id = id;
        this.type = type;
        this.maxPatients = maxPatients;
        this.currentPatients = 0;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getMaxPatients() {
        return maxPatients;
    }

    public int getCurrentPatients() {
        return currentPatients;
    }

    public boolean addPatient() {
        if (currentPatients < maxPatients) {
            currentPatients++;
            return true;
        }
        return false; // Cannot handle more patients
    }
    
    public void addOnePatient() {
    	if(currentPatients<maxPatients) {
    		currentPatients++;
    	}
    }

    // Method to remove a patient after treatment
    public void removePatient() {
        if (currentPatients > 0) {
            currentPatients--;
        }
    }

    public abstract boolean canTreatPatient(int triageCategory);
}
