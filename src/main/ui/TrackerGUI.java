package ui;

import model.*;
import persistence.*;
import ui.menus.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

// Workout Tracker Application with a Graphical User Interface
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
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            // EFFECTS: opens dialog with save options before closing
            @Override
            public void windowClosing(WindowEvent e) {
                popUpSaveDialog();
            }
        });

        exerciseList = new ExerciseList();
        workoutHistory = new WorkoutHistory();

        popUpLoadDialog();

        navBar = new JTabbedPane();
        navBar.setTabPlacement(JTabbedPane.TOP);

        loadMenuTabs();
        add(navBar);

        setVisible(true);

    }

    //getters
    public JTabbedPane getNavBar() {
        return navBar;
    }

    public ExerciseList getTrackerExerciseList() {
        return exerciseList;
    }

    public WorkoutHistory getTrackerWorkoutHistory() {
        return workoutHistory;
    }

    // EFFECTS: creates pop-up dialog with option to load data from previous save file
    private void popUpLoadDialog() {
        int loadOrNot = JOptionPane.showConfirmDialog(null, "Would you like to load data from previous save file?",
                "Load Option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (loadOrNot == JOptionPane.YES_OPTION) {
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

    // EFFECTS: creates pop-up dialog with option to save data to file before exiting, or cancel closing the frame
    private void popUpSaveDialog() {
        int saveOrNot = JOptionPane.showConfirmDialog(null, "Would you like to save data to file?",
                "Load Option", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (saveOrNot == JOptionPane.YES_OPTION) {
            saveData();
            System.exit(0);
        } else if (saveOrNot == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves data to file
    private void saveData() {
        try {
            writer.open();
            writer.write(exerciseList, workoutHistory);
            writer.close();
            JOptionPane.showConfirmDialog(null, "Data saved to file.", "File Write Success",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Cannot save to file with invalid name.", "File Write Error",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }
}
