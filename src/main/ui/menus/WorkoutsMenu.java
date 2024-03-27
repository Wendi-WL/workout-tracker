package ui.menus;

import model.*;
import ui.TrackerGUI;

import javax.swing.*;
import javax.swing.table.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class WorkoutsMenu extends Menu {
    // EFFECTS: constructs a workouts menu tab for console with create button
    public WorkoutsMenu(TrackerGUI tracker) {
        super(tracker);
        this.add(actionButtons());
        this.add(exerciseDetailsButton());
        displayTable();
    }

    // EFFECTS: returns exercise details button in new row
    protected JPanel exerciseDetailsButton() {
        JPanel buttonRow = new JPanel();
        JButton detailsButton = new JButton("View/Add/Remove Exercises");
        detailsButton.setEnabled(false);
        buttonRow.add(detailsButton);

        getTable().getSelectionModel().addListSelectionListener(evt -> {
            if (getTable().getSelectedRow() > -1) {
                detailsButton.setEnabled(true);
            }
        });

        return buttonRow;
    }

    // EFFECTS: returns JTable with workout fields as column names
    @Override
    protected JTable createJTable() {
        JTable table = new JTable(getTableModel());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);

        return table;
    }

    // EFFECTS: returns table model with appropriate fields as column names and rows of workouts
    private DefaultTableModel getTableModel() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (WorkoutFields field : WorkoutFields.values()) {
            model.addColumn(field.getString());
        }
        for (Workout w : getTracker().getTrackerWorkoutHistory().getWorkouts()) {
            Object[] workoutObject = {w.getDate(), w.getWorkoutType(), w.getLocation(),
                    w.workoutExercises()};
            model.addRow(workoutObject);
        }

        return model;
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

    // EFFECTS: places fields with workout's current values in edit menu dialog
    @Override
    protected Object[] placeEditableFields(Object[] selectedRow) {
        JTextField dateField = new JFormattedTextField(selectedRow[0]);
        JTextField workoutTypeField = new JTextField(selectedRow[1].toString());
        JTextField locationField = new JTextField(selectedRow[2].toString());

        return new Object[] {
                "Date (format YYYY-MM-DD):", dateField,
                "Workout Type (e.g., Legs):", workoutTypeField,
                "Location (e.g., ARC):", locationField
        };
    }

    // EFFECTS: returns values in each column of row as Object[]
    protected Object[] getRow(int selectedRowIndex) {
        Object[] row = new Object[4];
        for (int i = 0; i < 4; i++) {
            row[i] = getTable().getValueAt(selectedRowIndex, i);
        }
        return row;
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
        getTracker().getTrackerWorkoutHistory().addWorkout(workout);

        DefaultTableModel model = (DefaultTableModel) getTable().getModel();
        model.addRow(new Object[] {workout.getDate(), workoutType, location});
    }

    // MODIFIES: this
    // EFFECTS: parses field entries and updates the workout based on edited inputs
    @Override
    protected void handleEditedFieldInputs(Object[] old, Object[] edited, int rowIndex) {
        JTextField dateField = getJTextField(edited, 1);
        JTextField workoutTypeField = getJTextField(edited, 3);
        JTextField locationField = getJTextField(edited, 5);

        String dateString = dateField.getText();
        int year = Integer.parseInt(dateString.substring(0, 4));
        int month = Integer.parseInt(dateString.substring(5, 7));
        int day = Integer.parseInt(dateString.substring(8));
        LocalDate date = LocalDate.of(year, month, day);
        String workoutType = workoutTypeField.getText();
        String location = locationField.getText();

        Workout workout = rowObjectToWorkout(old);
        updateWorkout(workout, year, month, day, workoutType, location);
        updateRow(new Object[] {date, workoutType, location}, rowIndex, 3);
    }

    // EFFECTS: returns Workout represented by the row Object[]
    private Workout rowObjectToWorkout(Object[] o) {
        String dateString = o[0].toString();
        int year = Integer.parseInt(dateString.substring(0, 4));
        int month = Integer.parseInt(dateString.substring(5, 7));
        int day = Integer.parseInt(dateString.substring(8));
        return new Workout(year, month, day, o[1].toString(), o[2].toString());
    }

    // MODIFIES: this
    // EFFECTS: updates fields of Workout with edits
    private void updateWorkout(Workout workout, int year, int month, int day, String workoutType, String location) {
        for (Workout w : getTracker().getTrackerWorkoutHistory().getWorkouts()) {
            if (w.equals(workout)) {
                w.setDate(year, month, day);
                w.setWorkoutType(workoutType);
                w.setLocation(location);
                break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: converts array object to Workout, then deletes workout from tracker's list
    @Override
    protected void deleteExerciseOrWorkout(Object[] o) {
        Workout w = rowObjectToWorkout(o);
        deleteWorkout(w);
    }

    // MODIFIES: this
    // EFFECTS: deletes workout from tracker's list
    private void deleteWorkout(Workout w) {
        getTracker().getTrackerWorkoutHistory().removeWorkout(w);
    }
}
