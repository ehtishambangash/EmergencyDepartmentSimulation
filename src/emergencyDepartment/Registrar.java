package emergencyDepartment;

public class Registrar extends Physician {
    public Registrar(int id) {
        super(id, "Registrar", 4); // Registrars can treat up to 4 patients
    }

    @Override
    public boolean canTreatPatient(int triageCategory) {
        // Registrars can treat Category 1 and 2 patients
        return triageCategory == 1 || triageCategory == 2;
    }
}