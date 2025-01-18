package emergencyDepartment;

import java.util.Random;

public class ImpatientPatient {

    private static final double MEAN_PDDT = 156;

    public static void admitPatient(Patient patient,double pddt) {
        patient.getAdmissionStatus(true);
        
        patient.setPddt(pddt);
        System.out.println("Patient ID: " + patient.getId() + " has been admitted.");
    }

    public double calculatePDDT() {
        Random rand = new Random();
        double lambda = 1.0 / MEAN_PDDT;
        double random = rand.nextDouble(); 
        return -Math.log(1 - random) / lambda;
    }

   
}