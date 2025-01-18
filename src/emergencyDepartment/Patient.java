package emergencyDepartment;

public class Patient {
    private final int id;
    private final int triageCategory;
    private final int arrivalTime;
    protected int treatmentTime;
    protected int dischargeTime;
    protected int bedAssignmnetTime;
    private boolean bedAssigned;
    protected Bed assignedBed;
    protected Physician assignedPhysician;
    private boolean physicianAssigned;
    private boolean addmitted;
    private double pddt;
    

    public Patient(int id, int triageCategory, int arrivalTime) {
    	
        this.id = id;
        this.triageCategory = triageCategory;
        this.arrivalTime = arrivalTime;
        this.bedAssigned = false;
        
        this.physicianAssigned = false;
        this.treatmentTime = PearsonVIDistribution.calculateTreatmentTime(triageCategory);
        this.dischargeTime = arrivalTime + treatmentTime;
        //System.out.println("---------------------------------------------------------");
        //this.dischargeTime=0;
        this.bedAssignmnetTime=0;
        assignedPhysician = null;
        assignedBed = null;
    }
    
    public boolean getAdmissionStatus() {
        return addmitted;
    }

    public void getAdmissionStatus(boolean addmitted) {
        this.addmitted = addmitted;
    }

    public double getPddt() {
        return pddt;
    }

    public void setPddt(double pddt) {
        this.pddt = pddt;
    }
    
    public int getId() {
        return id;
    }
    public Bed getAllocatedBed() {
    	return assignedBed;
    }
    public Physician getAllocatedPhysician() {
    	return assignedPhysician;
    }
    
    
    public int getDischargeTime() {
        return dischargeTime;
    }

    public int getTriageCategory() {
        return triageCategory;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public boolean isBedAssigned() {
        return bedAssigned;
    }

    public void setBedAssigned(boolean bedAssigned) {
        this.bedAssigned = bedAssigned;
    }

    public boolean isPhysicianAssigned() {
        return physicianAssigned;
    }

    public void setPhysicianAssigned(boolean physicianAssigned) {
        this.physicianAssigned = physicianAssigned;
    }
    public void setPhysician(Physician physicianAssigned) {
        this.assignedPhysician = physicianAssigned;
    }
    public void setBedAssignmentTime(int time) {
        this.bedAssignmnetTime = time;
    }
    public void setBed(Bed bedAssigned) {
        this.assignedBed = bedAssigned;
    }
    public Physician getPhysician() {
        return assignedPhysician;
    }
    public Bed getBed() {
        return assignedBed;
    }
}
