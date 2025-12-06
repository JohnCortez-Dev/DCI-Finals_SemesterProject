/** John Cortez
 * December 5th, 2025
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Dci_Scores_Finals {
    // Number of bands is fixed at 12 as per requirements
    private static final int NUM_BANDS = 12;
    private static Scanner scanner = new Scanner(System.in);
    private static List<Band> bands = new ArrayList<>();

    public static void main(String[] args) {
        collectScores();
        calculateTotals();
        sortBands();
        displayResults();
        scanner.close();
    }

    /**
     * Represents a single marching band's scores and calculated totals.
     */
    {static class Dci_Scores_Finals {
        String name;
        // Sub-category scores (max 20.000 each)
        double brass, percussion, musicAnalysis, ensembleAnalysis;
        double visualProficiency, visualAnalysis, colorguard;
        double ge1, ge2, ge3, ge4;
        
        // Calculated category totals (max Music 30, Visual 30, GE 40)
        double totalMusic, totalVisual, totalGE, grandTotal;

        public Band(String name) {
            this.name = name;
        }

        // Comparator for sorting bands by grand total (descending for 1st to 12th order logic later)
        public static Comparator<Band> TotalScoreComparator = new Comparator<Band>() {
            public int compare(Band b1, Band b2) {
                return Double.compare(b2.grandTotal, b1.grandTotal);
            }
        };}
    }

    /**
     * Collects names and scores for all bands with validation.
     */
    private static void collectScores() {
        System.out.println("--- Marching Band Competition Score Entry ---");
        for (int i = 0; i < NUM_BANDS; i++) {
            System.out.println("\nEntering data for Band " + (i + 1) + ":");
            System.out.print("Enter Band Name: ");
            String name = scanner.nextLine();
            Band band = new Band(name);

            System.out.println("\nEnter Music Scores (max 20.000 points per sub-category):");
            band.brass = getValidScore("Brass: ");
            band.percussion = getValidScore("Percussion: ");
            band.musicAnalysis = getValidScore("Music Analysis: ");
            band.ensembleAnalysis = getValidScore("Ensemble Analysis: ");

            System.out.println("\nEnter Visual Scores (max 20.000 points per sub-category):");
            band.visualProficiency = getValidScore("Visual Proficiency: ");
            band.visualAnalysis = getValidScore("Visual Analysis: ");
            band.colorguard = getValidScore("Colorguard: ");

            System.out.println("\nEnter General Effect Scores (max 20.000 points per sub-category):");
            band.ge1 = getValidScore("General Effect 1: ");
            band.ge2 = getValidScore("General Effect 2: ");
            band.ge3 = getValidScore("General Effect 3: ");
            band.ge4 = getValidScore("General Effect 4: ");

            bands.add(band);
        }
    }

    /**
     * Prompts the user for a score and validates that it is within the 0.000 to 20.000 range.
     * @param prompt The message displayed to the user.
     * @return A valid score.
     */
    private static double getValidScore(String prompt) {
        double score = -1.0;
        while (true) {
            try {
                System.out.print(prompt);
                score = scanner.nextDouble();
                // Consume the newline character left by nextDouble()
                scanner.nextLine(); 
                if (score >= 0.0 && score <= 20.000) {
                    return score;
                } else {
                    System.out.println("Error: Score must be between 0.000 and 20.000. Please retry.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a numerical value.");
                // Consume the invalid input to prevent an infinite loop
                scanner.nextLine(); 
            }
        }
    }

    /**
     * Calculates the weighted total scores for each band based on competition rules.
     */
    private static void calculateTotals() {
        for (Band band : bands) {
            // Music: Average of 4 scores (max 20) is taken into account for total 30 points
            // Average = (s1+s2+s3+s4)/4. Weighting is 30/20 or 1.5 multiplier to average.
            double musicAvg = (band.brass + band.percussion + band.musicAnalysis + band.ensembleAnalysis) / 4.0;
            band.totalMusic = musicAvg * 1.5; // Scaled to max 30.000

            // Visual: Average of 3 scores (max 20) is taken into account for total 30 points
            // Weighting is 30/20 or 1.5 multiplier to average.
            double visualAvg = (band.visualProficiency + band.visualAnalysis + band.colorguard) / 3.0;
            band.totalVisual = visualAvg * 1.5; // Scaled to max 30.000

            // General Effect: Average of 4 scores (max 20) is taken into account for total 40 points
            // Weighting is 40/20 or 2.0 multiplier to average.
            double geAvg = (band.ge1 + band.ge2 + band.ge3 + band.ge4) / 4.0;
            band.totalGE = geAvg * 2.0; // Scaled to max 40.000

            band.grandTotal = band.totalMusic + band.totalVisual + band.totalGE;
        }
    }

    /**
     * Sorts the bands by their grand total score.
     */
    private static void sortBands() {
        // Sorts in descending order (highest score first)
        Collections.sort(bands, Band.TotalScoreComparator);
    }

    /**
     * Displays the final results, rankings, and special awards.
     */
    private static void displayResults() {
        System.out.println("\n\n--- Final Competition Results ---");
        System.out.printf("%-5s | %-30s | %-10s | %-10s\n", "Rank", "Band Name", "Total Score", "Status");
        System.out.println("--------------------------------------------------------------");

        // Display results from 12th to 1st place
        // The list is sorted descending (Index 0 is 1st place), so we iterate backwards for the initial display order
        for (int i = NUM_BANDS - 1; i >= 0; i--) {
            Band band = bands.get(i);
            int rank = i + 1; // Rank counter needs to be inverted if displaying 1st->12th
            // We want to display 12th place first (which is index 11), ending with 1st place (index 0)
            int displayRank = (NUM_BANDS - 1) - i + 1; // This gives us ranks 12 down to 1
            
            String status = "";
            if (i == 0) {
                status = "1st Place Winner!";
            }
            System.out.printf("%-5d | %-30s | %-10.3f | %-10s\n", displayRank, band.name, band.grandTotal, status);
        }

        displaySpecialAwards();
    }

    /**
     * Determines and displays winners of specific caption awards.
     */
    private static void displaySpecialAwards() {
        // Since the 'bands' list is sorted by Total Score (descending), we need separate logic for caption awards

        // Best Brass
        Band bestBrass = Collections.max(bands, Comparator.comparingDouble(b -> b.brass));
        // Best Percussion
        Band bestPercussion = Collections.max(bands, Comparator.comparingDouble(b -> b.percussion));
        // Best Visual Performance (using Visual Proficiency as proxy for "performance" aspect, though total visual could also be argued)
        Band bestVisual = Collections.max(bands, Comparator.comparingDouble(b -> b.totalVisual));
        // Best Colorguard
        Band bestColorguard = Collections.max(bands, Comparator.comparingDouble(b -> b.colorguard));
        // Best Overall General Effect
        Band bestGE = Collections.max(bands, Comparator.comparingDouble(b -> b.totalGE));
        
        System.out.println("\n--- Special Awards ---");
        System.out.println("Best Brass:                 " + bestBrass.name);
        System.out.println("Best Percussion:            " + bestPercussion.name);
        System.out.println("Best Visual Performance:    " + bestVisual.name);
        System.out.println("Best Colorguard:            " + bestColorguard.name);
        System.out.println("Best Overall General Effect: " + bestGE.name);
        System.out.println("\nOverall 1st Place:          " + bands.get(0).name); // Index 0 is the sorted winner
    }
}
