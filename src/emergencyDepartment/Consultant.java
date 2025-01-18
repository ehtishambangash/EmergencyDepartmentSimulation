package emergencyDepartment;

public class Consultant extends Physician {
    public Consultant(int id) {
        super(id, "Consultant", 4);
    }

    @Override
    public boolean canTreatPatient(int triageCategory) {
        // Consultants can treat Category 1 and 2 patients
        return triageCategory == 1 || triageCategory == 2;
    }
}