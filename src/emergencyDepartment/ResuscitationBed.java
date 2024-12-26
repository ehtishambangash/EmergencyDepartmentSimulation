package emergencyDepartment;

public class ResuscitationBed extends Bed {
    public ResuscitationBed(int id) {
        super(id, "Resuscitation");
    }

    @Override
    public boolean canAccommodatePatient(int triageCategory) {
        // Resuscitation bed can only accommodate Category 1 patients
        return triageCategory == 1;
    }
}