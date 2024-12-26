package emergencyDepartment;

public abstract class Bed {
    private int id;           // Unique identifier for the bed (0 to 23)
    private String type;      // Type of bed (e.g., Resuscitation, Acute, etc.)
    private boolean isAvailable; // Availability of the bed

    public Bed(int id, String type) {
        this.id = id;
        this.type = type;
        this.isAvailable = true; // Initially, the bed is available
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
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
