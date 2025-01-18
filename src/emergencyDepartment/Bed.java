package emergencyDepartment;

public abstract class Bed {
    private int id;           
    private String type;      // Type of bed
    private boolean isAvailable; 
    protected Patient assignedPatient;
    protected boolean firstAssign = false; 

    public Bed(int id, String type) {
        this.id = id;
        this.type = type;
        this.isAvailable = true;
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    
    public void firstAssign() {
    	firstAssign=true;
    }

    public String getType() {
        return type;
    }
    public Patient getPatient() {
        return assignedPatient;
    }
    public void assignPatient(Patient patient) {
    	assignedPatient = patient;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    // Abstract method to check if a patient can be accommodated
    public abstract boolean canAccommodatePatient(int triageCategory);
}
