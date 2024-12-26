package emergencyDepartment;

public class PearsonVIDistribution {

    private static final int MIN_CATEGORY_1_TIME = 5;
    private static final int MAX_CATEGORY_1_TIME = 10;

    private static final int MIN_CATEGORY_2_TIME = 7;
    private static final int MAX_CATEGORY_2_TIME = 13;

    private static final int MIN_CATEGORY_3_TIME = 10;
    private static final int MAX_CATEGORY_3_TIME = 15;

    private static final int MIN_CATEGORY_4_TIME = 15;
    private static final int MAX_CATEGORY_4_TIME = 7;

    private static final int MIN_CATEGORY_5_TIME = 16;
    private static final int MAX_CATEGORY_5_TIME = 10;

    public static int calculateTreatmentTime(int category) {
        switch (category) {
            case 1:
                return getRandomTreatmentTime(MIN_CATEGORY_1_TIME, MAX_CATEGORY_1_TIME);
            case 2:
                return getRandomTreatmentTime(MIN_CATEGORY_2_TIME, MAX_CATEGORY_2_TIME);
            case 3:
                return getRandomTreatmentTime(MIN_CATEGORY_3_TIME, MAX_CATEGORY_3_TIME);
            case 4:
                return getRandomTreatmentTime(MIN_CATEGORY_4_TIME, MAX_CATEGORY_4_TIME);
            case 5:
                return getRandomTreatmentTime(MIN_CATEGORY_5_TIME, MAX_CATEGORY_5_TIME);
            default:
                throw new IllegalArgumentException("Invalid category");
        }
    }

    private static int getRandomTreatmentTime(int minTime, int maxTime) {
        return (int) (Math.random() * (maxTime - minTime + 1)) + minTime;
    }
}
