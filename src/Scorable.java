
/**
 * Interface contract enforcing driving evaluation capabilities.
 * Contributed by: Hazim
 * Tested by: Mel, Mayar and Siti
 */
public interface Scorable {

    void addScore(int points);

    int getTotalScore();

    void saveScoreToFile();

    String getCurrentDriver();
}
