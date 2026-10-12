
public class LearningModule implements Displayable, GamificationSystem {

    //contributed by mayar 
    //tested by Mel, Hazim and Siti
    private String[][] screens; // each screen has multiple steps
    private int currentScreen;

    public LearningModule() {
        screens = new String[10][];
        // screen 1: intro contributed by siti
        screens[0] = new String[]{"Introduction to Health and Well-being",
            "Over the last 15 years, the number of childhood deaths has been cut in half.",
            "The new goal for worldwide Good Health promotes healthy lifestyles, preventive measures and modern, efficient healthcare for everyone."};
        //contributed by siti
        screens[1] = new String[]{"Definition of SDG TARGET 3.6",
            "By 2020, halve the number of global deaths and injuries from road traffic accidents."};
        //contributed by siti
        screens[2] = new String[]{"Statistics of Global Road Accident in Malaysia"};

        // screen 4: cause 1(contributed by mel)
        screens[3] = new String[]{
            "Cause 1: Speeding",
            "Speeding is a major cause of road accidents, but it is completely preventable.",
            "Driving too fast reduces your reaction time and increases the force of a crash.",
            "Fatality Risk: Pedestrian survival rates drop from 90% at 40km/h to only 10% at 100km/h.",
            "Breaking the speed limit in Malaysia can lead to heavy fines or jail time."
        };

        // screen 5: How to avoid speeding (contributed by mel)
        screens[4] = new String[]{
            "How to prevent speeding?",
            "Step 1: Plan your journey early to allow extra time and avoid the urge to rush.",
            "Step 2: Always pay attention to and stay within the legal posted speed limits.",
            "Step 3: Slow down instead of speeding up when approaching a yellow traffic light"
        };

        // screen 6: using mobile phone (contributed by mel)
        screens[5] = new String[]{
            "Cause 2: Using a mobile phone",
            "Using a phone while driving increases your accident risk by up to four times.",
            "Drivers talking on phones have an 18.1% slower reaction time.",
            "Phone use takes your eyes off the road and your hands off the steering wheel.",
            "Even hands-free sets cause dangerous mental distractions while driving.",
            "Using a phone illegally can result in heavy fines or jail time."
        };

        // screen 7: how to prevent mobile phone (contributed by hazim)
        screens[6] = new String[]{
            "How to prevent using mobile phone?",
            "Step 1: Lock your phone away or turn on 'Driving Mode' to silence text notifications automatically.",
            "Step 2: Pull over to a safe rest stop if handling a call or adjusting navigation is urgently needed.",
            "Step 3: Let passengers manage your device messaging parameters."
        };

        // screen 8: Cause 3 (contributed by hazim)
        screens[7] = new String[]{
            "Cause 3: Distracted Driving",
            "Distracted driving includes any secondary task that pulls your focus away from operating the car safely.",
            "Manual Distractions: Reaching down for loose items or eating behind the wheel.",
            "Visual Distractions: Eye-gaze drifting toward complex touchscreen infotainment system dashboards.",
            "Cognitive Distractions: Letting your mind drift off, zoning out, or experiencing high emotional stress."
        };

        // screen 9: How to prevent distracted driving
        screens[8] = new String[]{"How to prevent distracted driving?",
            "Step 1: Keep your eyes on the road",
            "Step 2: Avoid eating or multitasking while driving",
            "Step 3: Limit conversations that take your focus away",
            "Step 4: Use apps or devices that block notifications"
        };

        // screen 10: Summary
        screens[9] = new String[]{"Summary",
            "Step 1: Speeding is preventable :follow limits",
            "Step 2: Mobile phone use is dangerous :stay hands free",
            "Step 3: Distracted driving kills :stay focused",
            "Final Note: Awareness saves lives"
        };

        currentScreen = 0;
    }

    @Override
    public void showScreen(int screenNumber) {
        if (screenNumber >= 0 && screenNumber < screens.length) {
            System.out.println("Page " + (screenNumber + 1) + ":");
            for (String step : screens[screenNumber]) {
                System.out.println(" - " + step);
            }
            currentScreen = screenNumber;
        } else {
            System.out.println("Invalid page number");
        }
    }

    @Override
    public void nextScreen() {
        if (currentScreen < screens.length - 1) {
            showScreen(currentScreen + 1);
        } else {
            System.out.println("You are at the last page");
        }
    }

    @Override
    public void previousScreen() {
        if (currentScreen > 0) {
            showScreen(currentScreen - 1);
        } else {
            System.out.println("You are at the first page");
        }

    }

    @Override
    public void learningProgress() {
        int totalPages = 10;
        int current = currentScreen + 1;
        int percentage = (int) (((double) current / totalPages) * 100);

        System.out.println("Module Progress: " + percentage + "% Completed.");
    }

    public int getCurrentScreen() {
        return currentScreen;
    }

    public String[] getScreenContent(int screenNumber) {
        return screens[screenNumber];
    }

    // Method to trigger badge awarding and progress monitoring, contributed by mel
    @Override
    public void BadgeSystem() {
        // Handles internal message tracking for badge awarding and progress monitoring
        System.out.println("Badge system active and monitoring module milestones.");
    }

}
