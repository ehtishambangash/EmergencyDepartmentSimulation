package emergencyDepartment;

public class SeniorResident extends Physician {
    public SeniorResident(int id) {
        super(id, "Senior Resident", 4); // Senior Residents can treat up to 4 patients
    }

    @Override
    public boolean canTreatPatient(int triageCategory) {
        // Senior Residents can treat Category 3 to 5 patients under supervision
        return triageCategory >= 3 && triageCategory <= 5;
    }
}