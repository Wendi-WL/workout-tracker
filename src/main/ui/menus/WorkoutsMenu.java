package ui.menus;

import model.*;
import ui.TrackerGUI;

import javax.swing.*;
import java.text.SimpleDateFormat;

public class WorkoutsMenu extends Menu {
    // EFFECTS: constructs a workouts menu tab for console with create button
    public WorkoutsMenu(TrackerGUI tracker) {
        super(tracker);

        placeCreateButton();
    }

    // EFFECTS: places fields of appropriate format in workout creation menu dialog
    @Override
    protected Object[] placeFields() {
        JTextField dateField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        JTextField workoutTypeField = new JTextField();
        JTextField locationField = new JTextField();

        return new Object[] {
                "Date (format YYYY-MM-DD):", dateField,
                "Workout Type (e.g., Legs):", workoutTypeField,
                "Location (e.g., ARC):", locationField
        };
    }

    // MODIFIES: this
    // EFFECTS: parses field entries and creates a workout based on inputs
    @Override
    protected void handleFieldInputs(Object[] o) {
        JTextField dateField = getJTextField(o, 1);
        JTextField workoutTypeField = getJTextField(o, 3);
        JTextField locationField = getJTextField(o, 5);

        String dateString = dateField.getText();
        int year = Integer.parseInt(dateString.substring(0, 4));
        int month = Integer.parseInt(dateString.substring(5, 7));
        int day = Integer.parseInt(dateString.substring(8));
        String workoutType = workoutTypeField.getText();
        String location = locationField.getText();

        Workout workout = new Workout(year, month, day, workoutType, location);
        getTracker().getWorkoutHistory().addWorkout(workout);
    }
}
