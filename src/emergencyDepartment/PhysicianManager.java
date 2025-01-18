package emergencyDepartment;

import java.util.*;

public class PhysicianManager {
    protected List<Physician> physicians;

    public PhysicianManager(int juniorResidents, int seniorResidents, int interns, int consultants, int registrars) {
        physicians = new ArrayList<>();
        initializePhysicians(juniorResidents, seniorResidents, interns, consultants, registrars);
    }

    // Initialize the physicians with IDs according to categories
    private void initializePhysicians(int juniorResidents, int seniorResidents, int interns, int consultants, int registrars) {
        // Adding Junior Residents (Category 1)
        for (int i = 0; i < juniorResidents; i++) {
            physicians.add(new JuniorResident(i));
        }

        int seniorEnd = juniorResidents + seniorResidents;
        // Adding Senior Residents (Category 2)
        for (int i = juniorResidents; i < seniorEnd; i++) {
            physicians.add(new SeniorResident(i));
        }

        int internEnd = seniorEnd + interns;
        // Adding Interns (Category 3)
        for (int i = seniorEnd; i < internEnd; i++) {
            physicians.add(new Intern(i));
        }

        int consultantEnd = internEnd + consultants;
        // Adding Consultants (Category 4)
        for (int i = internEnd; i < consultantEnd; i++) {
            physicians.add(new Consultant(i));
        }

        int registrarEnd = consultantEnd + registrars;
        // Adding Registrars (Category 5)
        for (int i = consultantEnd; i < registrarEnd; i++) {
            physicians.add(new Registrar(i));
        }
    }



    public Physician allocatePhysician(int triageCategory) {
        for (Physician physician : physicians) {
            if (physician.canTreatPatient(triageCategory) && physician.addPatient()) {
                return physician;
            }
        }
        return null;
    }

    public void deallocatePhysician(Physician physician) {
        physician.removePatient();
    }

    public List<Physician> getPhysicians() {
        return physicians;
    }
    
    public int getPhysiciansnumber() {
        return physicians.size();
    }

    public void printPhysicians() {
        for (Physician physician : physicians) {
            System.out.println("Physician ID: " + physician.getId() + ", Type: " + physician.getType() + 
                               ", Current Patients: " + physician.getCurrentPatients() + 
                               ", Max Patients: " + physician.getMaxPatients());
        }
    }
}
