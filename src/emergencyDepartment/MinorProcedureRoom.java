package emergencyDepartment;

public class MinorProcedureRoom extends Bed {
    public MinorProcedureRoom(int id) {
        super(id, "Minor Procedure Room");
    }

    @Override
    public boolean canAccommodatePatient(int triageCategory) {
        // Minor procedure room can accommodate Category 2 to 5 patients
        return (triageCategory >= 2 && triageCategory <= 5) || triageCategory==1;
    }
}