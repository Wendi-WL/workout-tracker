package ui.menus;

import ui.TrackerGUI;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.*;

public class WorkoutsMenu extends Menu {
    //EFFECTS: constructs a home tab for console with buttons and a greeting
    public WorkoutsMenu(TrackerGUI tracker) {
        super(tracker);

        placeCreateButton();
    }

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

    @Override
    protected void handleFieldInputs(Object[] o) {
        JTextField dateField = getJTextField(o, 1);
        JTextField workoutTypeField = getJTextField(o, 3);
        JTextField locationField = getJTextField(o, 5);

        String dateString = dateField.getText();
        int year = Integer.parseInt(dateString.substring(0, 4));
        int month = Integer.parseInt(dateString.substring(5, 7));
        int day = Integer.parseInt(dateString.substring(8));
        LocalDate date = LocalDate.of(year, month, day);
        String workoutType = workoutTypeField.getText();
        String location = locationField.getText();
    }
}
