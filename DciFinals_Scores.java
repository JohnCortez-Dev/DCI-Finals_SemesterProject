/** John Cortez
 * December 5th, 2025
 * Semester Project: The Dci Finals
 * In this program, we will see the results of the 12 finalists that 
 * partook in the Dci World Championship Finals
 * We will see the ranking and scores in terms of Music
 * Visual, and General Effect, as well as diving deeper into sub-
 * categories such as Brass and Percussion performance.
 */

import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;

public class DciFinals_Scores {

    static class BandScore {
        String name;
        double brassScore;
        double percussionScore;
        double musicAnalysisScore;
        double ensembleAnalysisScore;
        double visualProficiencyScore;
        double visualAnalysisScore;
        double colorguardScore;
        double ge1Score;
        double ge2Score;
        double ge3Score;
        double ge4Score;
        double totalScore;
        double musicTotal;
        double visualTotal;
        double geTotal;

        public BandScore(String name) {
            this.name = name;
        }

        public void calculateTotals() {
            // Music Total: Average of the four sub-categories weighted to a max of 30.000
            // The average of (brass+perc+mus_an+ens_an) is out of 20.000 max points each, so max combined is 80.000.
            // The formula to scale is (Sum of subscores) * (Target Max / Max Possible Sum)
            // (brass + perc + mus_an + ens_an) * (30.000 / 80.000) = (Sum) * 0.375
            this.musicTotal = (brassScore + percussionScore + musicAnalysisScore + ensembleAnalysisScore) * 0.375;

            // Visual Total: Average of the three sub-categories weighted to a max of 30.000
            // Max combined is 60.000. (vis_p + vis_a + cg) * (30.000 / 60.000) = (Sum) * 0.5
            this.visualTotal = (visualProficiencyScore + visualAnalysisScore + colorguardScore) * 0.5;

            // General Effect Total: Average of the four sub-categories weighted to a max of 40.000
            // Max combined is 80.000. (ge1 + ge2 + ge3 + ge4) * (40.000 / 80.000) = (Sum) * 0.5
            this.geTotal = (ge1Score + ge2Score + ge3Score + ge4Score) * 0.5;

            this.totalScore = musicTotal + visualTotal + geTotal;
        }
    }

    private static final int NUM_BANDS = 12;
    private static final Scanner scanner = new Scanner(System.in);
    private static BandScore[] bands = new BandScore[NUM_BANDS];

    public static void main(String[] args) {
        initializeBands();
        inputScores();
        calculateAllTotals();
        sortBands();
        displayResults();
    }

    private static void initializeBands() {
        for (int i = 0; i < NUM_BANDS; i++) {
            System.out.printf("Enter name for band %d: ", i + 1);
            String name = scanner.nextLine();
            bands[i] = new BandScore(name);
        }
    }

    private static void inputScores() {
        for (BandScore band : bands) {
            System.out.printf("\nEntering scores for %s:\n", band.name);
            band.brassScore = getValidScore("Brass (max 20.000): ", 20.0);
            band.percussionScore = getValidScore("Percussion (max 20.000): ", 20.0);
            band.musicAnalysisScore = getValidScore("Music Analysis (max 20.000): ", 20.0);
            band.ensembleAnalysisScore = getValidScore("Ensemble Analysis (max 20.000): ", 20.0);
            band.visualProficiencyScore = getValidScore("Visual Proficiency (max 20.000): ", 20.0);
            band.visualAnalysisScore = getValidScore("Visual Analysis (max 20.000): ", 20.0);
            band.colorguardScore = getValidScore("Colorguard (max 20.000): ", 20.0);
            band.ge1Score = getValidScore("GE 1 (max 20.000): ", 20.0);
            band.ge2Score = getValidScore("GE 2 (max 20.000): ", 20.0);
            band.ge3Score = getValidScore("GE 3 (max 20.000): ", 20.0);
            band.ge4Score = getValidScore("GE 4 (max 20.000): ", 20.0);
        }
    }

    private static double getValidScore(String prompt, double max) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                double score = scanner.nextDouble();
                if (score >= 0 && score <= max) {
                    // Consume the rest of the line
                    if (scanner.hasNextLine()) {
                        scanner.nextLine();
                    }
                    return score;
                } else {
                    System.out.printf("Error: Score must be between 0 and %.3f.\n", max);
                }
            } else {
                System.out.println("Error: Invalid input. Please enter a numerical value.");
                scanner.next(); // Consume the invalid input
            }
        }
    }

    private static void calculateAllTotals() {
        for (BandScore band : bands) {
            band.calculateTotals();
        }
    }

    private static void sortBands() {
        // Sort bands in descending order (highest score first)
        Arrays.sort(bands, Comparator.comparingDouble((BandScore b) -> b.totalScore).reversed());
    }

    private static void displayResults() {
        // Find special award winners
        BandScore bestBrass = Arrays.stream(bands).max(Comparator.comparingDouble(b -> b.brassScore)).orElse(null);
        BandScore bestPercussion = Arrays.stream(bands).max(Comparator.comparingDouble(b -> b.percussionScore)).orElse(null);
        BandScore bestVisual = Arrays.stream(bands).max(Comparator.comparingDouble(b -> b.visualTotal)).orElse(null); // Based on calculated visual total (scaled)
        BandScore bestColorguard = Arrays.stream(bands).max(Comparator.comparingDouble(b -> b.colorguardScore)).orElse(null);
        BandScore bestGE = Arrays.stream(bands).max(Comparator.comparingDouble(b -> b.geTotal)).orElse(null); // Based on calculated GE total (scaled)
        BandScore firstPlace = bands[0]; // Already sorted

        System.out.println("\n--- Marching Band Competition Results ---");

        // Display individual awards
        System.out.printf("\n*** Award Winners ***\n");
        System.out.printf("Best Brass: %s (%.3f)\n", bestBrass.name, bestBrass.brassScore);
        System.out.printf("Best Percussion: %s (%.3f)\n", bestPercussion.name, bestPercussion.percussionScore);
        System.out.printf("Best Visual Performance: %s (%.3f)\n", bestVisual.name, bestVisual.visualTotal);
        System.out.printf("Best Colorguard: %s (%.3f)\n", bestColorguard.name, bestColorguard.colorguardScore);
        System.out.printf("Best Overall General Effect: %s (%.3f)\n", bestGE.name, bestGE.geTotal);
        System.out.printf("1st Place: %s (%.3f)\n", firstPlace.name, firstPlace.totalScore);


       System.out.println("\n--- Full Rankings (12th to 1st) ---");

 for (int i = NUM_BANDS - 1; i >= 0; i--) {
            BandScore b = bands[i];
            String highlight = "";
            if (b == firstPlace) {
                highlight = " <--- 1st Place OVERALL WINNER";
            } else if (b == bestBrass || b == bestPercussion || b == bestVisual || b == bestColorguard || b == bestGE) {
                 highlight = " <--- Award Winner";
            }
            // Rank should count up correctly from 12 to 1
            int rank = i + 1; // Incorrect rank logic if we reverse loop, need to adjust
            int displayRank = (NUM_BANDS) - i;
            System.out.printf("%d. %-20s Total Score: %.3f%s\n", displayRank, b.name, b.totalScore, highlight);
        }
    }
}