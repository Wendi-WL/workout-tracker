package ui;

import model.*;
import persistence.DataReader;
import persistence.DataWriter;
import ui.menus.*;

import javax.swing.*;
import java.io.IOException;

public class TrackerGUI extends JFrame {
    public static final int EXERCISES_MENU_INDEX = 0;
    public static final int WORKOUTS_MENU_INDEX = 1;

    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    private JTabbedPane navBar;
    private ExerciseList exerciseList;
    private WorkoutHistory workoutHistory;
    private final DataReader reader = new DataReader("./data/exercisesAndWorkouts.json");
    private final DataWriter writer = new DataWriter("./data/exercisesAndWorkouts.json");

    // MODIFIES: this
    // EFFECTS: creates TrackerGUI, with a navigation bar showing exercises menu tab and workouts menu tab
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

        popUpLoadDialog();
    }

    // EFFECTS: creates pop-up dialog with option to load data from previous save file
    private void popUpLoadDialog() {
        Object[] options = {
                "Yes",
                "No"
        };
        int loadOrNot = JOptionPane.showOptionDialog(null, "Would you like to load data from previous save file?", "Load Option",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (loadOrNot == JOptionPane.OK_OPTION) {
            loadData();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads data from previous save file
    private void loadData() {
        try {
            exerciseList = reader.readExerciseList();
            workoutHistory = reader.readWorkoutHistory();
            JOptionPane.showConfirmDialog(null, "Data loaded from save file.", "File Read Success",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "File does not exist", "File Read Error",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds exercises menu tab and workouts menu tab to this UI
    private void loadMenuTabs() {
        JPanel exercisesMenu = new ExercisesMenu(this);
        JPanel workoutsMenu = new WorkoutsMenu(this);

        navBar.add(exercisesMenu, EXERCISES_MENU_INDEX);
        navBar.setTitleAt(EXERCISES_MENU_INDEX, "Exercises");
        navBar.add(workoutsMenu, WORKOUTS_MENU_INDEX);
        navBar.setTitleAt(WORKOUTS_MENU_INDEX, "Workouts");
    }

    //getters
    public JTabbedPane getNavBar() {
        return navBar;
    }

    public ExerciseList getExerciseList() {
        return exerciseList;
    }

    public WorkoutHistory getWorkoutHistory() {
        return workoutHistory;
    }
}
