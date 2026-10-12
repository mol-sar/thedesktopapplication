
//20 multiple choice questions and true or false
//contributed by siti
//tested by Mel,Mayar and Hazim
public class QuizModule implements Quizzable {

    private String[] questions;
    private String[][] choices;
    private char[] correctAnswers;
    private int currentQuestionNumber;
    private int correctNumber;
    private boolean quizFinished;

    public QuizModule() {

        questions = new String[]{
            // multiple choice questions
            "What does the new goal for worldwide\nGood Health promotes?",
            "What is the SDG Target 3.6?",
            "What is major cause of road\naccidents in Malaysia?",
            "Drivers talking on phones have\nhow many % slower reaction time?",
            "How to silence text notifications automatically?",
            "Which distraction : Reaching down for loose\nitems or eating behind the wheel",
            "Which distraction : Eye-gaze drifting\ntoward complex touchscreen infotainment\nsystem dashboards",
            "Which distraction : Letting your mind drift off,\nzoning out, or experiencing high emotional stress",
            "When driving from 40km/h to 100km/h,\nthe pedestrian survival rates drop from 90% to?",
            // true or false questions
            "TRUE/FALSE: Breaking the speed limit in\nMalaysia can lead to heavy fines or jail times",
            "TRUE/FALSE: Using a mobile phone increases\nyour accident risk by up to 4 times",
            "TRUE/FALSE: Distracted driving includes\nany secondary task that pulls your\nfocus away from operating the car safely",
            "TRUE/FALSE: Avoid use apps/devices that\nblock notifications during driving",
            "TRUE/FALSE: Driving too fast reduces your\nreaction time and increases the force of a crash",
            "TRUE/FALSE : Slow down instead of speeding\nup when approaching a yellow traffic light",
            "TRUE/FALSE : Avoid planning your journey\n early and rush while driving to arrive faster",
            "TRUE/FALSE : Pull over to a safe rest\nstop if handling a call or adjusting\nnavigation is urgently needed",
            "TRUE/FALSE : No hands-free sets cause \ndangerous mental distractions while driving",
            "TRUE/FALSE : Distracted driving kills",
            "TRUE/FALSE : Awareness saves lives"
        };

        choices = new String[][]{
            //multiple choice questions
            {"A. Healthy lifestyles, preventive measures\nand modern, efficient healthcare for everyone", "B. Money, prosperity and peace", "C. Intelligence, high-thinking skills and high IQ"},
            {"A. Halve the number of global deaths and \ninjuries from road traffic accidents by 2019", "B. Halve the number of global deaths and injuries \nfrom road traffic accidents by 2020", "C. Halve the number of global deaths and injuries \nfrom road traffic accidents by 2021"},
            {"A. Beating red lights", "B. Speeding", "C. Reckless driving"},
            {"A. 80%", "B. 50%", "C. 18.1%"},
            {"A. Throwing your phone out of the window", "B. Dip your phone into the water", "C. Turn on 'Driving Mode'"},
            {"A. Manual", "B. Visual", "C. Cognitive"},
            {"A. Manual", "B. Visual", "C. Cognitive"},
            {"A. Manual", "B. Visual", "C. Cognitive"},
            {"A. 50%", "B. 100%", "C. 10%"},
            //true or false questions
            {"A. True", "B. False"},
            {"A. True", "B. False"},
            {"A. True", "B. False"},
            {"A. True", "B. False"},
            {"A. True", "B. False"},
            {"A. True", "B. False"},
            {"A. True", "B. False"},
            {"A. True", "B. False"},
            {"A. True", "B. False"},
            {"A. True", "B. False"},
            {"A. True", "B. False"}
        };

        // Verification Mapping Key
        correctAnswers = new char[]{
            // 9 multiple choice answers
            'A', 'B', 'B', 'C', 'C', 'A', 'B', 'C', 'C',
            // 11 true or false answers
            'A', 'A', 'A', 'B', 'A', 'A', 'B', 'A', 'B', 'A', 'A'

        };

        currentQuestionNumber = 0;
        correctNumber = 0;
        quizFinished = false;
    }

    @Override
    public void submitAnswer(char answer) throws InvalidScoreException {
        if (quizFinished) {
            throw new InvalidScoreException("Quiz ended.");
        }

        // Convert lowercase answers to uppercase for safety validation
        char upperAnswer = Character.toUpperCase(answer);
        if (upperAnswer < 'A' || upperAnswer > 'C') {
            throw new InvalidScoreException("Invalid answer choice: " + answer + ". Choose only A, B, C");
        }

        // Check answer
        if (upperAnswer == correctAnswers[currentQuestionNumber]) {
            correctNumber++;
        }

        // Advance or finalize state
        if (hasNextQuestion()) {
            nextQuestion();
        } else {
            quizFinished = true;
        }
    }

    //check if there are more questions to answer
    public boolean hasNextQuestion() {
        return currentQuestionNumber < questions.length - 1;
    }

    //move to the next question
    public void nextQuestion() {
        if (hasNextQuestion()) {
            currentQuestionNumber++;
        }
    }

    //get the current question to display
    public String getCurrentQuestion() {
        return "Question " + (currentQuestionNumber + 1) + "/" + questions.length + ":\n" + questions[currentQuestionNumber];
    }

    //get current optionn for question
    public String[] getCurrentOptions() {
        return choices[currentQuestionNumber];
    }

    public int getQuizScore() {
        // calculates score
        return (int) (((double) correctNumber / questions.length) * 100);
    }

    public int getTotalQuestions() {
        return questions.length;
    }

    //checks if question is finished
    public boolean isQuizFinished() {
        return quizFinished;
    }
}
