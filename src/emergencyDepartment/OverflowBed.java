package emergencyDepartment;

public class OverflowBed extends Bed {
    public OverflowBed(int id) {
        super(id, "Overflow Bed");
    }

    @Override
    public boolean canAccommodatePatient(int triageCategory) {
        // Overflow Bed can accommodate Category 1
        return (triageCategory == 1) ;
    }
}