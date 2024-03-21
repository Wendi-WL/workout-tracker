package ui.menus;

import ui.TrackerGUI;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public abstract class Menu extends JPanel {
    private final TrackerGUI tracker;

    // REQUIRES: TrackerGUI that holds this menu tab
    // EFFECTS: constructs Menu and initializes tracker field
    public Menu(TrackerGUI tracker) {
        this.tracker = tracker;
    }

    // EFFECTS: places create button that opens creation menu dialog on click
    protected void placeCreateButton() {
        JButton b1 = new JButton("+  Create");
        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.setSize(WIDTH, HEIGHT / 10);

        b1.addActionListener(evt -> createExerciseOrWorkoutDialog());

        this.add(buttonRow);
    }

    // EFFECTS: creates and returns row with button included
    private JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    // EFFECTS: opens creation menu dialog with appropriate fields and create/cancel options
    protected void createExerciseOrWorkoutDialog() {
        JFrame dialog = new JFrame();
        Object[] fields = placeFields();
        Object[] options = {
                "Create!",
                "Cancel"
        };
        int createOrCancel = JOptionPane.showOptionDialog(dialog, fields, "Creation Menu",
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

    //getter
    public TrackerGUI getTracker() {
        return tracker;
    }
}