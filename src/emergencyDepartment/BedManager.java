package emergencyDepartment;

import java.util.*;

public class BedManager {
    protected List<Bed> beds;

    public BedManager() {
        beds = new ArrayList<>();
        initializeBeds();
    }

    // Initialize the beds with IDs from 0 to 23
    private void initializeBeds() {
        // Adding Resuscitation Beds (only Category 1)
        for (int i = 0; i < 2; i++) {
            beds.add(new ResuscitationBed(i));
        }

        // Adding Acute Beds (Category 2-5)
        for (int i = 2; i < 10; i++) {
            beds.add(new AcuteBed(i));
        }

        // Adding Subacute Beds (Category 3-5)
        for (int i = 10; i < 15; i++) {
            beds.add(new SubacuteBed(i));
        }

        // Adding Minor Procedure Rooms (Category 2-5)
        for (int i = 15; i < 18; i++) {
            beds.add(new MinorProcedureRoom(i));
        }
        for (int i = 18; i < 25; i++) {
            beds.add(new OverflowBed(i));
        }
        for (int i = 25; i < 31; i++) {
            beds.add(new OverflowChair(i));
        }
        
    }

    // Allocate a bed based on the patient's triage category
    public Bed allocateBed(int triageCategory) {
        for (Bed bed : beds) {
            if (bed.isAvailable() && bed.canAccommodatePatient(triageCategory)) {
                bed.setAvailable(false); // Mark the bed as allocated
                return bed;
            }
        }
        return null; // No suitable bed available
    }

    // Deallocate a bed
    public void deallocateBed(Bed bed) {
        bed.setAvailable(true); // Mark the bed as available
    }

    // Get all beds
    public List<Bed> getBeds() {
        return beds;
    }
    
    public int getBedsnumber() {
        return beds.size();
    }

    // Print all beds (for debugging purposes)
    public void printBeds() {
        for (Bed bed : beds) {
            System.out.println("Bed ID: " + bed.getId() + ", Type: " + bed.getType() + ", Available: " + bed.isAvailable());
        }
    }
}