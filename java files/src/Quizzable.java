
//interface quizzable for quiz game
// contributed by Siti
//tested by Mel, Mayar and Hazim
public interface Quizzable {

    void submitAnswer(char answer) throws InvalidScoreException;

    boolean hasNextQuestion();

    void nextQuestion();

    String getCurrentQuestion();

    String[] getCurrentOptions();

    int getQuizScore();

    int getTotalQuestions();

    boolean isQuizFinished();
}
