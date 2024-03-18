package ui;

import model.*;
import ui.menus.*;

import javax.swing.*;

public class TrackerGUI extends JFrame {
    public static final int EXERCISES_MENU_INDEX = 0;
    public static final int WORKOUTS_MENU_INDEX = 1;

    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    private JTabbedPane navBar;
    private ExerciseList exerciseList;
    private WorkoutHistory workoutHistory;

    //MODIFIES: this
    //EFFECTS: creates TrackerGUI, loads welcome screen, displays ???
    public TrackerGUI() {
        super("Workout Tracker");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        exerciseList = new ExerciseList();
        workoutHistory = new WorkoutHistory();

        navBar = new JTabbedPane();
        navBar.setTabPlacement(JTabbedPane.TOP);

        loadMenuTabs();
        add(navBar);

        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: adds exercises menu tab and workouts menu tab to this UI
    private void loadMenuTabs() {
        JPanel exercisesMenu = new ExercisesMenu(this);
        JPanel workoutsMenu = new WorkoutsMenu(this);

        navBar.add(exercisesMenu, EXERCISES_MENU_INDEX);
        navBar.setTitleAt(EXERCISES_MENU_INDEX, "Exercises");
        navBar.add(workoutsMenu, WORKOUTS_MENU_INDEX);
        navBar.setTitleAt(WORKOUTS_MENU_INDEX, "Workouts");
    }

    //EFFECTS: returns navigation bar of this UI
    public JTabbedPane getNavBar() {
        return navBar;
    }
}
