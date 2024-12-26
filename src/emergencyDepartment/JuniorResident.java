package emergencyDepartment;

public class JuniorResident extends Physician {
    public JuniorResident(int id) {
        super(id, "Junior Resident", 3); // Junior Residents can treat up to 3 patients
    }

    @Override
    public boolean canTreatPatient(int triageCategory) {
        // Junior Residents can treat Category 3 to 5 patients under supervision
        return triageCategory >= 3 && triageCategory <= 5;
    }
}