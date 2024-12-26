package emergencyDepartment;

import java.util.Random;

public class WeibullDistribution {
    private double lambda ;  // Scale parameter
    private double k;       // Shape parameter

    public WeibullDistribution() {
        this.lambda = 10;
        this.k = 1.5;
    }

    public int generateArrivalTime() {
        Random random = new Random();
        double u = random.nextDouble();
        return (int) (lambda * Math.pow(-Math.log(1 - u), 1 / k));
    }
}
