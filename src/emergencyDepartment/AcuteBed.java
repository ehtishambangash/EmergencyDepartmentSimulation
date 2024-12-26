package emergencyDepartment;

public class AcuteBed extends Bed {
    public AcuteBed(int id) {
        super(id, "Acute");
    }

    @Override
    public boolean canAccommodatePatient(int triageCategory) {
        // Acute bed can accommodate Category 2 to 5 patients
        return (triageCategory >= 1 && triageCategory <= 5) ;
    }
}