package ui.menus;

import ui.TrackerGUI;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;

public abstract class Menu extends JPanel {
    private final TrackerGUI tracker;

    // REQUIRES: TrackerGUI that holds this menu tab
    // EFFECTS: constructs Menu and initializes tracker field
    public Menu(TrackerGUI tracker) {
        this.tracker = tracker;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    //getter
    public TrackerGUI getTracker() {
        return tracker;
    }

    // EFFECTS: places create button that opens creation menu dialog on click
    protected void placeCreateButton() {
        JButton createButton = new JButton("+  Create");
        JPanel buttonRow = new JPanel();
        buttonRow.add(createButton);
        buttonRow.setSize(TrackerGUI.WIDTH, TrackerGUI.HEIGHT);

        createButton.addActionListener(evt -> createExerciseOrWorkoutDialog());

        this.add(buttonRow);
    }

    // EFFECTS: displays table of exercises with columns being names of fields
    protected void displayTable() {
        JTable table = createJTable();

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JPanel panel = new JPanel();
        panel.setSize(1000, 1000);
        panel.add(scrollPane);

        this.add(panel);
    }

    // EFFECTS: returns JTable with appropriate TableModel
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

    // EFFECTS: places fields of appropriate format in creation menu dialog
    protected abstract Object[] placeFields();

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

    // EFFECTS: parses field entries and creates appropriate object based on inputs
    protected abstract void handleFieldInputs(Object[] o);
}