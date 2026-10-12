//contributed by Mayar
// tested by Mel, Hazim and Siti

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class LearningModuleGUI extends JFrame {

    private LearningModule lm;       // Learning module logic
    private JTextArea contentArea;   // Area to display text
    private JProgressBar progressBar; // Progress bar to show learning progress
    private JLabel badgeNotification; // Label to show achievement badge notifications
    private JLabel infographicLabel;  // Container for dynamic infographics
    private JButton nextButton;       // Field to allow dynamic finish button text conversion

    // Constructor updated to link with MainMenuGUI dashboard
    public LearningModuleGUI(MainMenuGUI mainMenu, ScoreManager sharedScore) {
        this.lm = new LearningModule();

        // FRAME SETUP
        setTitle("Learning Module");
        setSize(360, 740);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Safeguards the parent frame process thread execution
        setLocationRelativeTo(null);

        // HEADER PANEL
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(46, 204, 113));

        // TITLE LABEL
        JLabel titleLabel = new JLabel("Learning Module");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 51, 102));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // IMAGE LOGO
        JLabel imageLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon("logo.jpg");
            Image img = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imageLabel.setIcon(new ImageIcon("logo.jpeg"));
        }
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // PROGRESS BAR SETUP
        int totalPages = 10;
        int progressPercent = (int) (((double) (lm.getCurrentScreen() + 1) / totalPages) * 100);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(progressPercent);
        progressBar.setStringPainted(true);
        progressBar.setString("Learning Progress: " + progressPercent + "%");

        // BADGE NOTIFICATION
        badgeNotification = new JLabel("");
        badgeNotification.setFont(new Font("Arial", Font.BOLD, 11));
        badgeNotification.setForeground(Color.YELLOW);
        badgeNotification.setBackground(new Color(46, 150, 36));
        badgeNotification.setOpaque(false);
        badgeNotification.setHorizontalAlignment(SwingConstants.CENTER);
        badgeNotification.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(imageLabel, BorderLayout.CENTER);
        headerPanel.add(progressBar, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        // CENTRAL BOX CONTAINER
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(224, 255, 255));

        contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        contentArea.setBackground(new Color(224, 255, 255));
        centerPanel.add(contentArea);

        // TEXT WRAP
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        infographicLabel = new JLabel("", JLabel.CENTER);
        infographicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infographicLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 15, 10));
        centerPanel.add(infographicLabel);

        add(new JScrollPane(centerPanel), BorderLayout.CENTER);

        // BUTTON PANEL
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel innerButtons = new JPanel();
        buttonPanel.setBackground(new Color(224, 255, 255));
        innerButtons.setBackground(new Color(224, 255, 255));
        JButton prevButton = new JButton("Previous");
        nextButton = new JButton("  Next   ");
        Dimension buttonSize = new Dimension(150, 35);
        prevButton.setPreferredSize(buttonSize);
        nextButton.setPreferredSize(buttonSize);

        innerButtons.add(prevButton);
        innerButtons.add(nextButton);
        buttonPanel.add(badgeNotification, BorderLayout.NORTH);
        buttonPanel.add(innerButtons, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // BUTTON ACTIONS
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lm.previousScreen();
                updateContent();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lm.getCurrentScreen() == 9) {
                    dispose();
                } else {
                    lm.nextScreen();
                    updateContent();
                }
            }
        });

        // SHOW INITIAL SLIDE
        updateContent();
        setVisible(true);
    }

    private void updateContent() {
        contentArea.setText("");
        int current = lm.getCurrentScreen();
        String[] steps = lm.getScreenContent(current);

        // UPDATE PROGRESS BAR
        int totalPages = 10;
        int progressPercent = (int) (((double) (current + 1) / totalPages) * 100);
        progressBar.setValue(progressPercent);
        progressBar.setString("Learning Progress: " + progressPercent + "%");

        // DYNAMICALLY UPDATE NEXT BUTTON TEXT TO "Finish" ON LAST PAGE
        if (current == 9) {
            nextButton.setText("Finish");
        } else {
            nextButton.setText("  Next   ");
        }

        // CHECKPOINT BADGE TRIGGERS BASED ON CURRENT SCREEN
        if (current == 3) {
            triggerBadgeNotification("Speed Governor", "Master speed safety!");

        } else if (current == 5) {
            triggerBadgeNotification("Focused Driver", "Avoid phone hazards!");

        } else if (current == 7) {
            triggerBadgeNotification("Safety Advocate", "Eliminate road distractions!");

        } else if (current == 9) {
            triggerBadgeNotification("SDG Ambassador", "Ready for the Quiz!");

        }

        // TRIGGER IMAGES FOR SPECIFIC SCREENS
        if (current == 2) { // Screen 3: Statistics of Global Road Accident in Malaysia
            updateDisplayImage("stat.png");
        } else if (current == 6) { // Screen 7: Phone Prevention
            updateDisplayImage("phone_prevention.png");
        } else if (current == 7) { // Screen 8: Distracted Driving
            updateDisplayImage("distracted_driving.png");
        } else {
            infographicLabel.setIcon(null);
            infographicLabel.setText("");
        }

        if (steps.length > 0) {
            contentArea.append("\n\n          " + steps[0] + "\n");
        }

        for (int i = 1; i < steps.length; i++) {
            contentArea.append(" • " + steps[i] + "\n");
        }

        SwingUtilities.invokeLater(() -> contentArea.setCaretPosition(0));
    }

    private void updateDisplayImage(String fileName) {
        try {
            ImageIcon rawIcon = new ImageIcon(fileName);
            if (rawIcon.getIconWidth() == -1) {
                infographicLabel.setText("[ Image Asset '" + fileName + "' Missing from Project Folder ]");
                infographicLabel.setIcon(null);
                infographicLabel.setForeground(Color.RED);
            } else {
                int targetWidth = 320;
                int targetHeight = 240;

                BufferedImage sharpBuffer = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = sharpBuffer.createGraphics();

                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.drawImage(rawIcon.getImage(), 0, 0, targetWidth, targetHeight, null);
                g2.dispose();

                infographicLabel.setIcon(new ImageIcon(sharpBuffer));
                infographicLabel.setText("");
            }
        } catch (Exception ex) {
            infographicLabel.setText("Error rendering image framework.");
        }
    }
    //AUTOMATIC POP-UP BADGE NOTIFICATION SYSTEM

    private void triggerBadgeNotification(String badgeName, String description) {
        badgeNotification.setOpaque(true);
        badgeNotification.setText("[ UNLOCKED ] " + badgeName + ": " + description);
        badgeNotification.revalidate();
        badgeNotification.repaint();

        javax.swing.Timer timer = new javax.swing.Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                badgeNotification.setText("");
                badgeNotification.setOpaque(false);
                badgeNotification.revalidate();
                badgeNotification.repaint();
            }
        });
        timer.setRepeats(false); // Ensure the timer only runs once per notification
        timer.start();
    }
}
