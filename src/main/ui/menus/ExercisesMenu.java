package ui.menus;

import ui.TrackerGUI;

import javax.swing.*;

public class ExercisesMenu extends Menu {
    //EFFECTS: constructs a home tab for console with buttons and a greeting
    public ExercisesMenu(TrackerGUI tracker) {
        super(tracker);

        super.placeCreateButton();
    }

    @Override
    protected Object[] placeFields() {
        JTextField nameField = new JTextField();
        JTextField exerciseTypeField = new JTextField();
        JFormattedTextField weightField = new JFormattedTextField(0.0);
        JFormattedTextField setsField = new JFormattedTextField(0);
        JFormattedTextField repsField = new JFormattedTextField(0);
        JFormattedTextField restTimeField = new JFormattedTextField(0);

        return new Object[] {
                "Name (e.g., Shoulder press):", nameField,
                "Exercise Type (e.g., Upper body):", exerciseTypeField,
                "Weight (in lbs - e.g., 12.5):", weightField,
                "Sets (e.g., 3):", setsField,
                "Reps (e.g., 10):", repsField,
                "Rest time (in s - e.g., 60):", restTimeField
        };
    }

    @Override
    protected void handleFieldInputs(Object[] o) {
        JTextField nameField = getJTextField(o, 1);
        JTextField exerciseTypeField = getJTextField(o, 3);
        JTextField weightField = getJTextField(o, 5);
        JTextField setsField = getJTextField(o, 7);
        JTextField repsField = getJTextField(o, 9);
        JTextField restTimeField = getJTextField(o, 11);

        String name = nameField.getText();
        String exerciseType = exerciseTypeField.getText();
        double weight = Double.parseDouble(weightField.getText());
        int sets = Integer.parseInt(setsField.getText());
        int reps = Integer.parseInt(repsField.getText());
        int restTime = Integer.parseInt(restTimeField.getText());
    }
}
