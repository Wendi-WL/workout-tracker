package ui.menus;

import ui.TrackerGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

// Menu in a tab bar
public abstract class Menu extends JPanel {
    private final TrackerGUI tracker;
    private JTable table;

    // REQUIRES: TrackerGUI that holds this menu tab
    // EFFECTS: constructs Menu and initializes tracker field
    public Menu(TrackerGUI tracker) {
        this.tracker = tracker;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        table = createJTable();
    }

    //getters
    public TrackerGUI getTracker() {
        return tracker;
    }

    public JTable getTable() {
        return table;
    }

    // EFFECTS: returns panel of first row of buttons that open dialogs corresponding to their actions
    protected JPanel actionButtons() {
        JButton createButton = getCreateButton();
        JButton editButton = getEditButton();
        JButton deleteButton = getDeleteButton();
        JPanel buttonRow = formatButtonRow(createButton, editButton, deleteButton);

        createButton.addActionListener(evt -> createExerciseOrWorkoutDialog());
        table.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting() && table.getSelectedRow() > -1) {
                for (ActionListener al : editButton.getActionListeners()) {
                    editButton.removeActionListener(al);
                }
                for (ActionListener al : deleteButton.getActionListeners()) {
                    deleteButton.removeActionListener(al);
                }
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
                int rowIndex = table.getSelectedRow();
                editButton.addActionListener(e -> editExerciseOrWorkoutDialog(rowIndex));
                deleteButton.addActionListener(e -> deleteExerciseOrWorkoutDialog(rowIndex));
            }
        });
        return buttonRow;
    }

    // EFFECTS: returns row of buttons
    protected static JPanel formatButtonRow(JButton createButton, JButton editButton, JButton deleteButton) {
        JPanel buttonRow = new JPanel();
        buttonRow.add(createButton);
        buttonRow.add(editButton);
        buttonRow.add(deleteButton);
        buttonRow.setSize(TrackerGUI.WIDTH, TrackerGUI.HEIGHT);
        return buttonRow;
    }

    // EFFECTS: returns create button with icon
    private static JButton getCreateButton() {
        Icon createIcon = new ImageIcon("./data/createIcon.png");
        JButton createButton = new JButton(createIcon);
        createButton.setText("Create");
        return createButton;
    }

    // EFFECTS: returns edit button with icon
    private static JButton getEditButton() {
        Icon editIcon = new ImageIcon("./data/editIcon.png");
        JButton editButton = new JButton(editIcon);
        editButton.setText("Edit");
        editButton.setEnabled(false);
        return editButton;
    }

    // EFFECTS: returns delete button with icon
    private static JButton getDeleteButton() {
        Icon deleteIcon = new ImageIcon("./data/trashIcon.png");
        JButton deleteButton = new JButton(deleteIcon);
        deleteButton.setText("Delete");
        deleteButton.setEnabled(false);
        return deleteButton;
    }

    // EFFECTS: displays table of exercises with columns being names of fields
    protected void displayTable() {
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JPanel panel = new JPanel();
        panel.setSize(1000, 1000);
        panel.add(scrollPane);

        this.add(panel);
    }

    // EFFECTS: returns JTable with appropriate DefaultTableModel
    protected abstract JTable createJTable();

    // EFFECTS: opens creation menu dialog with appropriate fields and create/cancel options
    protected void createExerciseOrWorkoutDialog() {
        Object[] fields = placeFields();
        Object[] options = {
                "Create!",
                "Cancel"
        };
        int createOrCancel = JOptionPane.showOptionDialog(null, fields, "Creation Menu",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, fields);
        if (createOrCancel == JOptionPane.OK_OPTION) {
            handleFieldInputs(fields);
        }
    }

    // EFFECTS: opens edit menu dialog with appropriate fields containing current values, and save/cancel options
    protected void editExerciseOrWorkoutDialog(int selectedRowIndex) {
        Object[] selectedRow = getRow(selectedRowIndex);
        Object[] fields = placeEditableFields(selectedRow);
        Object[] options = {
                "Save Edits",
                "Cancel"
        };

        int editOrCancel = JOptionPane.showOptionDialog(null, fields, "Edit Menu",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, fields);
        if (editOrCancel == JOptionPane.OK_OPTION) {
            handleEditedFieldInputs(selectedRow, fields, selectedRowIndex);
        }
    }

    // EFFECTS: opens deletion dialog with option to delete exercise or workout from row of table and tracker's list
    protected void deleteExerciseOrWorkoutDialog(int selectedRowIndex) {
        Object[] selectedRow = getRow(selectedRowIndex);
        int deleteOrNot = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Deletion Menu",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (deleteOrNot == JOptionPane.YES_OPTION) {
            ((DefaultTableModel) table.getModel()).removeRow(selectedRowIndex);
            deleteExerciseOrWorkout(selectedRow);
        }
    }

    // EFFECTS: places fields of appropriate format in creation menu dialog
    protected abstract Object[] placeFields();

    // EFFECTS: places fields with current values in edit menu dialog
    protected abstract Object[] placeEditableFields(Object[] selectedRow);

    // EFFECTS: returns values in each column of row as Object[]
    protected abstract Object[] getRow(int selectedRowIndex);

    // EFFECTS: returns a NumberFormatter of the specified numerical type and minimum
    protected NumberFormatter setNumberFormatter(String type, Number min) {
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(format);
        if (type.equals("int")) {
            numberFormatter.setValueClass(Integer.class);
            numberFormatter.setMinimum((int) min);
        } else if (type.equals("double")) {
            numberFormatter.setValueClass(Double.class);
            numberFormatter.setMinimum((double) min);
        }
        numberFormatter.setCommitsOnValidEdit(true);
        return numberFormatter;
    }

    // EFFECTS: returns the JTextField at the specified index of o
    protected JTextField getJTextField(Object[] o, int index) {
        return (JTextField) o[index];
    }

    // EFFECTS: parses field entries, adds to tracker's list and adds row to table, based on inputs
    protected abstract void handleFieldInputs(Object[] o);

    // EFFECTS: parses field entries, updates tracker's list and updates row in table, based on edits
    protected abstract void handleEditedFieldInputs(Object[] old, Object[] edited, int rowIndex);

    // EFFECTS: updates values in each column of row given a rowObject
    protected void updateRow(Object[] rowObject, int rowIndex, int numberOfColumns) {
        for (int i = 0; i < numberOfColumns; i++) {
            getTable().setValueAt(rowObject[i], rowIndex, i);
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes exercise or workout from tracker's list
    protected abstract void deleteExerciseOrWorkout(Object[] o);
}