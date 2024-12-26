package emergencyDepartment;

public class Intern extends Physician {
    public Intern(int id) {
        super(id, "Intern", 2); // Interns can treat up to 2 patients
    }

    @Override
    public boolean canTreatPatient(int triageCategory) {
        // Interns can treat Category 3 to 5 patients under supervision
        return triageCategory >= 3 && triageCategory <= 5;
    }
}