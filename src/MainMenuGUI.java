
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Visual Smartphone GUI Hub and Application Main Menu. Coordinates Learning
 * Modules, Quizzes, and persistent data logging scores. Contributed by: Hazim
 * and Mel Tested by Mayar and Siti
 */
public class MainMenuGUI extends JFrame {

    private ScoreManager scoreEngine;
    private JLabel scoreLabel;
    private JTextArea contentArea;
    private JLabel imageLabel;
    private JPanel centerScrollContent;

    // Tracks historical peak quiz points to avoid duplicate point stacking on reattempts.
    private int highestQuizContribution = 0;

    public MainMenuGUI(String userName) {
        this.scoreEngine = new ScoreManager(userName);

        // 1. Setup Fixed Mobile App Window Frame
        setTitle("Sustainable Development Goals 3.6 - Road Safety Training");
        setSize(400, 720);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(Color.WHITE);

        // 2. Premium Header Status Display
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        headerPanel.setBackground(new Color(24, 28, 36));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel userLabel = new JLabel("Active Driver: " + scoreEngine.getCurrentDriver(), JLabel.LEFT);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        scoreLabel = new JLabel("Current Safety Points: " + scoreEngine.getTotalScore() + " / 100", JLabel.LEFT);
        scoreLabel.setForeground(new Color(46, 204, 113));
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        headerPanel.add(userLabel);
        headerPanel.add(scoreLabel);
        add(headerPanel, BorderLayout.NORTH);

        // 3. Central Dynamic Scrolling Viewport
        centerScrollContent = new JPanel();
        centerScrollContent.setLayout(new BoxLayout(centerScrollContent, BoxLayout.Y_AXIS));
        centerScrollContent.setBackground(Color.WHITE);
        centerScrollContent.setBorder(new EmptyBorder(15, 15, 15, 15));

        contentArea = new JTextArea("Welcome to the Sustainable Development Goals 3.6 Learning Application!\n\n"
                + "Our mission is to help halve global deaths and injuries from road traffic accidents by increasing awareness.\n\n"
                + "Please select an option below to start your training or test your defensive driving knowledge.");
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentArea.setForeground(new Color(44, 62, 80));
        contentArea.setBackground(Color.WHITE);
        contentArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerScrollContent.add(contentArea);

        centerScrollContent.add(Box.createRigidArea(new Dimension(0, 15)));

        imageLabel = new JLabel("", JLabel.CENTER);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerScrollContent.add(imageLabel);

        updateDisplayImage("logo.jpeg");

        JScrollPane scrollPane = new JScrollPane(centerScrollContent);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // 4. Sleek Bottom Action Navigation Panel
        JPanel navigationPanel = new JPanel(new GridLayout(6, 1, 8, 8));
        navigationPanel.setBackground(Color.WHITE);
        navigationPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        JButton btnLaunchLearning = createStyledButton("Explore Learning Module");
        JButton btnLaunchQuiz = createStyledButton("Take Quiz");
        JButton btnTrackMilestones = createStyledButton("View Score Milestones");
        JButton btnRedeemRewards = createStyledButton("Redeem Rewards");
        JButton btnSaveLogs = createStyledButton("Save Data");
        JButton btnExitApp = createStyledButton("Exit");

        btnExitApp.setBackground(new Color(231, 76, 60));
        btnExitApp.setForeground(Color.WHITE);

        navigationPanel.add(btnLaunchLearning);
        navigationPanel.add(btnLaunchQuiz);
        navigationPanel.add(btnRedeemRewards);
        navigationPanel.add(btnTrackMilestones);
        navigationPanel.add(btnSaveLogs);
        navigationPanel.add(btnExitApp);
        add(navigationPanel, BorderLayout.SOUTH);

        //  INTERACTIVE ACTIONS 
        // 1. Launch Learning Window Frame
        btnLaunchLearning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LearningModuleGUI(MainMenuGUI.this, scoreEngine);
                refreshScoreDisplay();
            }
        });

        // 2. Launch Quiz Module 
        btnLaunchQuiz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuizModule quiz = new QuizModule();
                boolean takingQuiz = true;

                JOptionPane.showMessageDialog(MainMenuGUI.this,
                        "Starting the SDG Target 3.6 Safety Assessment!\nAnswer choices using letters A, B, or C.",
                        "Quiz Module Initialized", JOptionPane.INFORMATION_MESSAGE);

                while (!quiz.isQuizFinished()) {
                    String questionText = quiz.getCurrentQuestion() + "\n\nOptions:\n";
                    for (String option : quiz.getCurrentOptions()) {
                        questionText += option + "\n";
                    }

                    String input = JOptionPane.showInputDialog(MainMenuGUI.this, questionText, "Quiz Question", JOptionPane.QUESTION_MESSAGE);

                    if (input == null) {
                        takingQuiz = false;
                        break;
                    }

                    if (input.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(MainMenuGUI.this, "Answer field cannot be empty!", "Input Error", JOptionPane.WARNING_MESSAGE);
                        continue;
                    }

                    try {
                        char submittedAnswer = input.trim().charAt(0);
                        quiz.submitAnswer(submittedAnswer);
                    } catch (InvalidScoreException ex) {
                        JOptionPane.showMessageDialog(MainMenuGUI.this, ex.getMessage(), "Invalid Input Exception Raised", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (takingQuiz) {

                    int potentialPoints = quiz.getQuizScore();

                    // Evaluate if user beat their previous attempt record
                    if (potentialPoints > highestQuizContribution) {
                        int pointGainDifference = potentialPoints - highestQuizContribution;
                        scoreEngine.addScore(pointGainDifference); // Add only the improved margin
                        highestQuizContribution = potentialPoints; // Cache new peak performance
                    } else {
                        JOptionPane.showMessageDialog(MainMenuGUI.this,
                                "Reattempt finished! Your score did not exceed your historical high. No additional points added.",
                                "Score Unchanged", JOptionPane.INFORMATION_MESSAGE);
                    }

                    refreshScoreDisplay();

                    // Text descriptor evaluated dynamically based on achievements
                    String motivationalMessage;
                    if (potentialPoints >= 80) {
                        motivationalMessage = "Outstanding!";
                    } else if (potentialPoints >= 60) {
                        motivationalMessage = "That's good!";
                    } else if (potentialPoints >= 40) {
                        motivationalMessage = "Good try!";
                    } else if (potentialPoints >= 20) {
                        motivationalMessage = "You can do better!";
                    } else {
                        motivationalMessage = "Don't give up!";
                    }

                    JOptionPane.showMessageDialog(MainMenuGUI.this,
                            "Quiz Completed!\n"
                            + "Score: " + potentialPoints + "%\n\n" + motivationalMessage,
                            "Assessment Finished", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // 3. Track Milestones and Redeem Rewards
        btnTrackMilestones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoreEngine.trackRewards();
                refreshScoreDisplay();
            }
        });
        btnRedeemRewards.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoreEngine.redeemRewards();
                refreshScoreDisplay();
            }
        });

        // 4. Backup Progress Indices
        btnSaveLogs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoreEngine.saveScoreToFile();
                JOptionPane.showMessageDialog(MainMenuGUI.this, "Driving metrics successfully written to driver_logs.txt!");
            }
        });

        // 5. Exit Application Process
        btnExitApp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(0, 42));
        return button;
    }

    private void updateDisplayImage(String fileName) {
        try {
            ImageIcon rawIcon = new ImageIcon(fileName);
            if (rawIcon.getIconWidth() == -1) {
                imageLabel.setText("[ Image Asset '" + fileName + "' Missing from Project Folder ]");
                imageLabel.setIcon(null);
                imageLabel.setForeground(Color.RED);
            } else {
                int targetWidth = 340;
                int targetHeight = 260;

                BufferedImage sharpBuffer = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = sharpBuffer.createGraphics();

                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.drawImage(rawIcon.getImage(), 0, 0, targetWidth, targetHeight, null);
                g2.dispose();

                imageLabel.setIcon(new ImageIcon(sharpBuffer));
                imageLabel.setText("");
            }
        } catch (Exception ex) {
            imageLabel.setText("Error rendering image framework.");
        }
        centerScrollContent.revalidate();
        centerScrollContent.repaint();
    }

    public void refreshScoreDisplay() {
        scoreLabel.setText("Current Safety Points: " + scoreEngine.getTotalScore() + " / 100");
    }

    public static class Main {

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                new MainMenuGUI("Active Driver").setVisible(true);
            });
        }
    }
}
