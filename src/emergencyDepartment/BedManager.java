package emergencyDepartment;

import java.util.*;

public class BedManager {
    protected List<Bed> beds;

    public BedManager(int RBeds,int ABeds,int SBeds,int MBeds,int OBeds,int OChairs) {
        beds = new ArrayList<>();
        initializeBeds(RBeds,ABeds,SBeds,MBeds,OBeds,OChairs);
    }

    // Initialize the beds
    private void initializeBeds(int RBeds,int ABeds,int SBeds,int MBeds,int OBeds,int OChairs) {
        // Adding Resuscitation Beds (only Category 1)
        for (int i = 0; i < RBeds; i++) {
            beds.add(new ResuscitationBed(i));
        }
        
        int AcuteEnd = RBeds + ABeds;
        // Adding Acute Beds (Category 2-5)
        for (int i = RBeds; i < AcuteEnd; i++) {
            beds.add(new AcuteBed(i));
        }
        int SubAcuteEnd = AcuteEnd + SBeds;
        // Adding Subacute Beds (Category 3-5)
        for (int i = AcuteEnd; i <SubAcuteEnd ; i++) {
            beds.add(new SubacuteBed(i));
        }
        int MinorProcedureEnd = SubAcuteEnd + MBeds;
        // Adding Minor Procedure Rooms (Category 2-5)
        for (int i = SubAcuteEnd; i < MinorProcedureEnd; i++) {
            beds.add(new MinorProcedureRoom(i));
        }
        int OBedsEnd = MinorProcedureEnd + OBeds;
        for (int i = MinorProcedureEnd; i < OBedsEnd; i++) {
            beds.add(new OverflowBed(i));
        }
        int OChairsEnd = OBedsEnd + OChairs;
        for (int i = OBedsEnd; i < OChairsEnd; i++) {
            beds.add(new OverflowChair(i));
        }
        
    }


    public Bed allocateBed(int triageCategory) {
        for (Bed bed : beds) {
            if (bed.isAvailable() && bed.canAccommodatePatient(triageCategory)) {
                bed.setAvailable(false);
                return bed;
            }
        }
        return null;
    }

    // Deallocate a bed
    public void deallocateBed(Bed bed) {
        bed.setAvailable(true);
    }

    // Get all beds
    public List<Bed> getBeds() {
        return beds;
    }
    
    public int getBedsnumber() {
        return beds.size();
    }

    public void printBeds() {
        for (Bed bed : beds) {
            System.out.println("Bed ID: " + bed.getId() + ", Type: " + bed.getType() + ", Available: " + bed.isAvailable());
        }
    }
}