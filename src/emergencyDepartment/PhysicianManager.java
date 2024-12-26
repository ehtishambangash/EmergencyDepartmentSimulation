package emergencyDepartment;

import java.util.*;

public class PhysicianManager {
    protected List<Physician> physicians;

    public PhysicianManager() {
        physicians = new ArrayList<>();
        initializePhysicians();
    }

    // Initialize physicians with IDs and roles
    private void initializePhysicians() {
        // Adding Junior Residents
        for (int i = 0; i < 4; i++) {
            physicians.add(new JuniorResident(i));
        }

        // Adding Senior Residents
        for (int i = 4; i < 8; i++) {
            physicians.add(new SeniorResident(i));
        }

        // Adding Interns
        for (int i = 8; i < 12; i++) {
            physicians.add(new Intern(i));
        }

        // Adding Consultants
        for (int i = 12; i < 20; i++) {
            physicians.add(new Consultant(i));
        }

        // Adding Registrars
        for (int i = 20; i < 24; i++) {
            physicians.add(new Registrar(i));
        }
    }


    // Allocate a physician based on triage category
    public Physician allocatePhysician(int triageCategory) {
        for (Physician physician : physicians) {
            if (physician.canTreatPatient(triageCategory) && physician.addPatient()) {
                return physician;
            }
        }
        return null; // No suitable physician available
    }

    // Deallocate a physician after treatment
    public void deallocatePhysician(Physician physician) {
        physician.removePatient();
    }

    // Get all physicians
    public List<Physician> getPhysicians() {
        return physicians;
    }
    
    public int getPhysiciansnumber() {
        return physicians.size();
    }

    // Print all physicians (for debugging purposes)
    public void printPhysicians() {
        for (Physician physician : physicians) {
            System.out.println("Physician ID: " + physician.getId() + ", Type: " + physician.getType() + 
                               ", Current Patients: " + physician.getCurrentPatients() + 
                               ", Max Patients: " + physician.getMaxPatients());
        }
    }
}
