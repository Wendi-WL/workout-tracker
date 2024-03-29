package ui.menus;

import model.*;
import ui.TrackerGUI;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

// Menu with workouts in the workout history and buttons for associated actions
public class WorkoutsMenu extends Menu {
    private JTable exercisesTable;
    private JTable exerciseListTable;

    // EFFECTS: constructs a workouts menu tab for console with appropriate buttons and workout history table
    public WorkoutsMenu(TrackerGUI tracker) {
        super(tracker);
        this.add(actionButtons());
        this.add(exerciseDetailsButton());
        this.add(tableScrollPane());
    }

    // EFFECTS: returns exercise details button in new row
    protected JPanel exerciseDetailsButton() {
        JPanel buttonRow = new JPanel();
        JButton detailsButton = new JButton("View/Add/Remove Exercises");
        detailsButton.setEnabled(false);
        buttonRow.add(detailsButton);

        getTable().getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting() && getTable().getSelectedRow() > -1) {
                for (ActionListener al : detailsButton.getActionListeners()) {
                    detailsButton.removeActionListener(al);
                }
                detailsButton.setEnabled(true);
                int rowIndex = getTable().getSelectedRow();
                detailsButton.addActionListener(e -> detailsDialog(rowIndex));
            }
        });

        return buttonRow;
    }

    // EFFECTS: opens new frame with workout details, exercise add/remove buttons, and workout exercises table
    private void detailsDialog(int selectedRowIndex) {
        JFrame detailsFrame = new JFrame("Workout Details and Exercises");
        detailsFrame.setSize(TrackerGUI.WIDTH, TrackerGUI.HEIGHT);

        Object[] selectedRow = getRow(selectedRowIndex);
        Workout workoutNoExercises = rowObjectToWorkout(selectedRow);
        Workout workout = null;
        for (Workout w : getTracker().getTrackerWorkoutHistory().getWorkouts()) {
            if (w.equals(workoutNoExercises)) {
                workout = w;
            }
        }
        JPanel panel = new JPanel();
        if (workout != null) {
            panel = getDetailsPanel(workout);
        }

        detailsFrame.add(panel);
        detailsFrame.setVisible(true);
    }

    // EFFECTS: returns panel with workout details, exercise buttons and table
    private JPanel getDetailsPanel(Workout w) {
        JPanel panel = getWorkoutDetailsPanel(w);

        JPanel buttonRow = new JPanel();
        JButton addButton = new JButton("+  Add");
        JButton removeButton = new JButton("X  Remove");
        removeButton.setEnabled(false);
        buttonRow.add(addButton);
        buttonRow.add(removeButton);
        panel.add(buttonRow);

        exercisesTable = exercisesJTable(w);
        panel.add(exercisesTableScrollPane(exercisesTable));

        addButton.addActionListener(evt -> addExerciseDialog(w));
        exercisesTable.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting() && exercisesTable.getSelectedRow() > -1) {
                for (ActionListener al : removeButton.getActionListeners()) {
                    removeButton.removeActionListener(al);
                }
                removeButton.setEnabled(true);
                removeButton.addActionListener(e -> removeExerciseDialog(exercisesTable.getSelectedRow(), w));
            }
        });

        return panel;
    }

    // EFFECTS: returns panel with workout details
    private JPanel getWorkoutDetailsPanel(Workout w) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Workout:");
        title.setFont(new Font("Serif", Font.BOLD, 24));
        JLabel workoutDetails = new JLabel(w.workoutDetails());
        workoutDetails.setFont(new Font("Serif", Font.PLAIN, 20));
        JLabel heading = new JLabel("Exercises:");
        heading.setFont(new Font("Serif", Font.BOLD, 22));
        panel.add(title);
        panel.add(workoutDetails);
        panel.add(heading);
        return panel;
    }

    // EFFECTS: returns scroll pane with table of exercises with columns being names of fields
    private JScrollPane exercisesTableScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        return scrollPane;
    }

    // EFFECTS: returns JTable with exercise fields as column names
    private JTable exercisesJTable(Workout w) {
        JTable table = new JTable(getExercisesTableModel(w));
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
    private DefaultTableModel getExercisesTableModel(Workout w) {
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

        for (Exercise e : w.getExercises().getExerciseList()) {
            Object[] exerciseObject = {e.getName(), e.getExerciseType(), e.getWeight(), e.getSets(), e.getReps(),
                    e.getRestTime(), e.getNote()};
            model.addRow(exerciseObject);
        }

        return model;
    }

    // MODIFIES: this
    // EFFECTS: opens add exercise dialog with instructions and table of all exercises to select from and add to workout
    private void addExerciseDialog(Workout w) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new Label("Select an exercise from the list of all exercises to add to the workout"));
        panel.add(exerciseListJTable());
        int addOrNot = JOptionPane.showConfirmDialog(null, panel, "Add Menu",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (addOrNot == JOptionPane.YES_OPTION) {
            if (exerciseListTable.getSelectedRow() > -1) {
                Object[] o = getExerciseListRow(exerciseListTable.getSelectedRow());
                ((DefaultTableModel) exercisesTable.getModel()).addRow(o);
                Exercise e = new Exercise(o[0].toString(), o[1].toString(), (double) o[2], (int) o[3], (int) o[4],
                        (int) o[5], o[6].toString());
                w.addExercise(e);
            }
        }
    }

    // EFFECTS: returns JTable using exercise table model and settings, sets column sizes
    private JTable exerciseListJTable() {
        exerciseListTable = new JTable(getExerciseListTableModel());
        exerciseListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        exerciseListTable.setRowSelectionAllowed(true);
        exerciseListTable.setColumnSelectionAllowed(false);

        TableColumn column;
        for (int i = 0; i < 7; i++) {
            column = exerciseListTable.getColumnModel().getColumn(i);
            if (i == 3 || i == 4) {
                column.setPreferredWidth(50);
            } else {
                column.setPreferredWidth(100);
            }
        }

        return exerciseListTable;
    }

    // EFFECTS: returns table model with exercise fields as column names and rows of exercises
    private DefaultTableModel getExerciseListTableModel() {
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

    // EFFECTS: returns exercise array object from exercise list table specified by selected index
    private Object[] getExerciseListRow(int selectedRowIndex) {
        Object[] row = new Object[7];
        for (int i = 0; i < 7; i++) {
            row[i] = exerciseListTable.getValueAt(selectedRowIndex, i);
        }
        return row;
    }

    // EFFECTS: opens remove exercise dialog with option to remove exercise from workout
    private void removeExerciseDialog(int selectedRowIndex, Workout w) {
        Object[] selectedRow = getExerciseRow(selectedRowIndex);
        int removeOrNot = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to remove this exercise from the workout?", "Remove Menu",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (removeOrNot == JOptionPane.YES_OPTION) {
            ((DefaultTableModel) exercisesTable.getModel()).removeRow(selectedRowIndex);
            removeExercise(selectedRow, w);
        }
    }

    // EFFECTS: returns exercise array object from the workout's exercises table
    private Object[] getExerciseRow(int selectedRowIndex) {
        Object[] row = new Object[7];
        for (int i = 0; i < 7; i++) {
            row[i] = exercisesTable.getValueAt(selectedRowIndex, i);
        }
        return row;
    }

    // MODIFIES: this
    // EFFECTS: removes exercise from workout's exercise list
    private void removeExercise(Object[] o, Workout w) {
        Exercise e = new Exercise(o[0].toString(), o[1].toString(), (double) o[2], (int) o[3], (int) o[4], (int) o[5],
                o[6].toString());
        w.getExercises().getExerciseList().remove(e);
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

    // EFFECTS: returns table model with workout fields as column names and rows of workouts
    protected DefaultTableModel getTableModel() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("Date");
        model.addColumn("Workout Type");
        model.addColumn("Location");

        for (Workout w : getTracker().getTrackerWorkoutHistory().getWorkouts()) {
            Object[] workoutObject = {w.getDate(), w.getWorkoutType(), w.getLocation()};
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
        Object[] row = new Object[3];
        for (int i = 0; i < 3; i++) {
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
    // EFFECTS: converts array object to a Workout, then deletes workout from tracker's list
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
