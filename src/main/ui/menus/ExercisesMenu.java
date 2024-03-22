package ui.menus;

import model.*;
import ui.TrackerGUI;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

public class ExercisesMenu extends Menu {
    // EFFECTS: constructs an exercises menu tab for console with create button
    public ExercisesMenu(TrackerGUI tracker) {
        super(tracker);

        placeCreateButton();
        displayTable();
    }

    // EFFECTS: returns JTable with exercise fields as column names, sets column sizes
    @Override
    protected JTable createJTable() {
        DefaultTableModel model = new DefaultTableModel();
        for (ExerciseFields field : ExerciseFields.values()) {
            model.addColumn(field.getString());
        }
        for (Exercise e : getTracker().getExerciseList().getExerciseList()) {
            Object[] exerciseObject = {e.getName(), e.getExerciseType(), e.getWeight(), e.getSets(), e.getReps(),
                    e.getRestTime(), e.getNote()};
            model.addRow(exerciseObject);
        }

        JTable table = new JTable(model);

        TableColumn column;
        for (int i = 0; i < 7; i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 3 || i == 4) {
                column.setPreferredWidth(50);
            } else {
                column.setPreferredWidth(100);
            }
        }

        return table;
    }

    // EFFECTS: places fields of appropriate format in exercise creation menu dialog
    @Override
    protected Object[] placeFields() {
        JTextField nameField = new JTextField();
        JTextField exerciseTypeField = new JTextField();

        NumberFormatter doubleFormatter = setNumberFormatter("double", 0.0);
        NumberFormatter intFormatter = setNumberFormatter("int", 0);
        NumberFormatter intOneFormatter = setNumberFormatter("int", 1);

        JFormattedTextField weightField = new JFormattedTextField(doubleFormatter);
        JFormattedTextField setsField = new JFormattedTextField(intOneFormatter);
        JFormattedTextField repsField = new JFormattedTextField(intOneFormatter);
        JFormattedTextField restTimeField = new JFormattedTextField(intFormatter);

        return new Object[] {
                "Name (e.g., Shoulder press):", nameField,
                "Exercise Type (e.g., Upper body):", exerciseTypeField,
                "Weight (in lbs - e.g., 12.5):", weightField,
                "Sets (e.g., 3):", setsField,
                "Reps (e.g., 10):", repsField,
                "Rest time (in s - e.g., 60):", restTimeField
        };
    }

    // EFFECTS: parses field entries and creates an exercise based on inputs
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

        Exercise exercise = new Exercise(name, exerciseType, weight, sets, reps, restTime);
        getTracker().getExerciseList().create(exercise);
    }
}
