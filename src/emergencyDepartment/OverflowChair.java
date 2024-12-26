package emergencyDepartment;

public class OverflowChair extends Bed {
    public OverflowChair(int id) {
        super(id, "Overflow Chair");
    }

    @Override
    public boolean canAccommodatePatient(int triageCategory) {
        // Overflow Chair can accommodate Category 2
        return (triageCategory == 2) ;
    }
}