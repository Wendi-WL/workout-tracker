package ui.menus;

import model.*;
import ui.TrackerGUI;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

// Menu with the exercises in the exercise list and buttons for associated actions
public class ExercisesMenu extends Menu {
    // EFFECTS: constructs an exercises menu tab for console with appropriate buttons and exercise list table
    public ExercisesMenu(TrackerGUI tracker) {
        super(tracker);
        this.add(actionButtons());
        this.add(tableScrollPane());
    }

    // EFFECTS: returns JTable using appropriate table model and settings, sets column sizes
    @Override
    protected JTable createJTable() {
        JTable table = new JTable(getTableModel());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);

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

    // EFFECTS: returns table model with exercise fields as column names and rows of exercises
    protected DefaultTableModel getTableModel() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("Name");
        model.addColumn("Exercise Type");
        model.addColumn("Weight");
        model.addColumn("Sets");
        model.addColumn("Reps");
        model.addColumn("Rest Time");
        model.addColumn("Note(s)");

        for (Exercise e : getTracker().getTrackerExerciseList().getExerciseList()) {
            Object[] exerciseObject = {e.getName(), e.getExerciseType(), e.getWeight(), e.getSets(), e.getReps(),
                    e.getRestTime(), e.getNote()};
            model.addRow(exerciseObject);
        }

        return model;
    }

    // EFFECTS: places fields of appropriate format in exercise creation menu dialog
    @Override
    protected Object[] placeFields() {
        JTextField nameField = new JTextField();
        JTextField exerciseTypeField = new JTextField();
        JTextField noteField = new JTextField();

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
                "Rest time (in s - e.g., 60):", restTimeField,
                "Note (optional):", noteField
        };
    }

    // EFFECTS: places fields with exercise's current values in edit menu dialog
    @Override
    protected Object[] placeEditableFields(Object[] selectedRow) {
        JTextField nameField = new JTextField(selectedRow[0].toString());
        JTextField exerciseTypeField = new JTextField(selectedRow[1].toString());
        JTextField noteField = new JTextField(selectedRow[6].toString());

        JFormattedTextField weightField = new JFormattedTextField(selectedRow[2]);
        JFormattedTextField setsField = new JFormattedTextField(selectedRow[3]);
        JFormattedTextField repsField = new JFormattedTextField(selectedRow[4]);
        JFormattedTextField restTimeField = new JFormattedTextField(selectedRow[5]);

        return new Object[] {
                "Name (e.g., Shoulder press):", nameField,
                "Exercise Type (e.g., Upper body):", exerciseTypeField,
                "Weight (in lbs - e.g., 12.5):", weightField,
                "Sets (e.g., 3):", setsField,
                "Reps (e.g., 10):", repsField,
                "Rest time (in s - e.g., 60):", restTimeField,
                "Note (optional):", noteField
        };
    }

    // EFFECTS: returns values in each column of row as Object[]
    protected Object[] getRow(int selectedRowIndex) {
        Object[] row = new Object[7];
        for (int i = 0; i < 7; i++) {
            row[i] = getTable().getValueAt(selectedRowIndex, i);
        }
        return row;
    }

    // MODIFIES: this
    // EFFECTS: parses field entries and creates an exercise based on inputs
    @Override
    protected void handleFieldInputs(Object[] o) {
        JTextField nameField = getJTextField(o, 1);
        JTextField exerciseTypeField = getJTextField(o, 3);
        JTextField weightField = getJTextField(o, 5);
        JTextField setsField = getJTextField(o, 7);
        JTextField repsField = getJTextField(o, 9);
        JTextField restTimeField = getJTextField(o, 11);
        JTextField noteField = getJTextField(o, 13);

        String name = nameField.getText();
        String exerciseType = exerciseTypeField.getText();
        double weight = Double.parseDouble(weightField.getText());
        int sets = Integer.parseInt(setsField.getText());
        int reps = Integer.parseInt(repsField.getText());
        int restTime = Integer.parseInt(restTimeField.getText());
        String note = noteField.getText();

        Exercise exercise = new Exercise(name, exerciseType, weight, sets, reps, restTime, note);
        getTracker().getTrackerExerciseList().create(exercise);

        DefaultTableModel model = (DefaultTableModel) getTable().getModel();
        model.addRow(new Object[] {name, exerciseType, weight, sets, reps, restTime, note});
    }

    // MODIFIES: this
    // EFFECTS: parses field entries and updates the exercise based on edited inputs
    @Override
    protected void handleEditedFieldInputs(Object[] old, Object[] edited, int rowIndex) {
        JTextField nameField = getJTextField(edited, 1);
        JTextField exerciseTypeField = getJTextField(edited, 3);
        JTextField weightField = getJTextField(edited, 5);
        JTextField setsField = getJTextField(edited, 7);
        JTextField repsField = getJTextField(edited, 9);
        JTextField restTimeField = getJTextField(edited, 11);
        JTextField noteField = getJTextField(edited, 13);

        String name = nameField.getText();
        String exerciseType = exerciseTypeField.getText();
        double weight = Double.parseDouble(weightField.getText());
        int sets = Integer.parseInt(setsField.getText());
        int reps = Integer.parseInt(repsField.getText());
        int restTime = Integer.parseInt(restTimeField.getText());
        String note = noteField.getText();

        Exercise exercise = rowObjectToExercise(old);
        updateExercise(exercise, name, exerciseType, weight, sets, reps, restTime, note);
        updateRow(new Object[] {name, exerciseType, weight, sets, reps, restTime, note}, rowIndex, 7);
    }

    // EFFECTS: returns Exercise represented by the row Object[]
    public Exercise rowObjectToExercise(Object[] o) {
        return new Exercise(o[0].toString(), o[1].toString(), (double) o[2], (int) o[3], (int) o[4], (int) o[5],
                o[6].toString());
    }

    // MODIFIES: this
    // EFFECTS: updates fields of Exercise with edits
    private void updateExercise(Exercise exercise, String name, String exerciseType, double weight, int sets, int reps,
                                int restTime, String note) {
        for (Exercise e : getTracker().getTrackerExerciseList().getExerciseList()) {
            if (e.equals(exercise)) {
                e.setName(name);
                e.setExerciseType(exerciseType);
                e.setWeight(weight);
                e.setSets(sets);
                e.setReps(reps);
                e.setRestTime(restTime);
                e.setNote(note);
                break;
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: converts array object to an Exercise, then deletes exercise from tracker's list
    @Override
    protected void deleteExerciseOrWorkout(Object[] o) {
        Exercise e = rowObjectToExercise(o);
        deleteExercise(e);
    }

    // MODIFIES: this
    // EFFECTS: deletes exercise from tracker's list
    private void deleteExercise(Exercise e) {
        getTracker().getTrackerExerciseList().delete(e);
    }
}
