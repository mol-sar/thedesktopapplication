
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Robust driving metrics analyzer and persistent data logging tracker.
 * Contributed by: Hazim and Mel Tested by: Mayar and Siti
 */
public class ScoreManager implements Scorable, Rewardable {

    private String driverName;
    private int totalScore;
    private int countBadges = 0;

    public ScoreManager(String driverName) {
        this.driverName = driverName;
        this.totalScore = 0;
    }

    // Scorable interface methods for adding scores and retrieving total score and driver profile, contributed by Hazim
    @Override
    public void addScore(int points) {
        if (totalScore + points <= 100) {
            this.totalScore += points;
        } else {
            this.totalScore = 100; // Enforce maximum point cap safety parameters
        }
    }

    // Method to reset score for a new session or profile, contributed by Hazim
    @Override
    public int getTotalScore() {
        return this.totalScore;
    }

    // Method to retrieve the current driver profile name, contributed by Hazim
    @Override
    public String getCurrentDriver() {
        return this.driverName;
    }

    // Method to save the current score and driver profile to a persistent log file, contributed by Hazim
    @Override
    public void saveScoreToFile() {
        try (FileWriter fw = new FileWriter("driver_logs.txt", true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println("Driver Profile: " + driverName);
            pw.println("Accumulated Safety Evaluation Score: " + totalScore + " / 100 Points");
            pw.println("---------------------------------------------------------");
        } catch (IOException e) {
            System.err.println("Fatal System Exception writing to disk: " + e.getMessage());
        }

    }

    // Rewardable interface methods for redeeming points and tracking rewards, contributed by Mel
    @Override
    public void redeemRewards() {
        if (this.totalScore >= 50) {
            this.totalScore -= 50;
            this.countBadges++;
            javax.swing.JOptionPane.showMessageDialog(null,
                    "You've redeemed 50 reward points for a badge! Total Badges: " + countBadges);
        } else {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "You need at least 50 reward points to redeem a badge.\nCurrent Reward Points: " + totalScore);
        }
    }

    // Method to track rewards and display current points and badges, contributed by Mel
    @Override
    public void trackRewards() {
        javax.swing.JOptionPane.showMessageDialog(null,
                "--- DRIVER ACHIEVEMENT STATUS ---\n"
                + "Current Points Balance: " + totalScore + "\n"
                + "Badges Purchased: " + countBadges + "\n"
        );
    }
}
