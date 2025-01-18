package emergencyDepartment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CalculateTriage {
    private final List<String> diseases;
    private final Map<String, double[]> probabilities;
    private final Random random;
    protected String Disease;

    public CalculateTriage(String fileName) {
        diseases = new ArrayList<>();
        probabilities = new HashMap<>();
        random = new Random();
        loadDiseaseData(fileName);
    }

    private void loadDiseaseData(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",\\s*");
                String disease = parts[0];
                double[] probs = new double[5];
                for (int i = 0; i < 5; i++) {
                    probs[i] = Double.parseDouble(parts[i + 1]);
                }
                diseases.add(disease);
                probabilities.put(disease, probs);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading the diseases file: " + e.getMessage());
        }
    }
    
    public String DiseaseGetter() {
    	return Disease;
    }


    public int triageCal() {
        int randomIndex = random.nextInt(diseases.size());
        String disease = diseases.get(randomIndex);
        Disease = disease;
        
        double[] probs = probabilities.get(disease);
        double randomValue = random.nextDouble();

        double cumulative = 0.0;
        for (int i = 0; i < probs.length; i++) {
            cumulative += probs[i];
            if (randomValue <= cumulative) {
                //System.out.println("Disease: " + disease); // Print the disease
                return i + 1; // Return the category (1 to 5)
            }
        }
        return 5;
    }
}
