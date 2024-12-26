package emergencyDepartment;

public class SubacuteBed extends Bed {
    public SubacuteBed(int id) {
        super(id, "Subacute");
    }

    @Override
    public boolean canAccommodatePatient(int triageCategory) {
        // Subacute bed can accommodate Category 3 to 5 patients
        return (triageCategory >= 3 && triageCategory <= 5) || triageCategory==1;
    }
}