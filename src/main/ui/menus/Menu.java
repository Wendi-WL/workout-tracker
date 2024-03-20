package ui.menus;

import ui.TrackerGUI;

import javax.swing.*;
import java.awt.*;

public abstract class Menu extends JPanel {
    private final TrackerGUI tracker;

    //REQUIRES: TrackerGUI that holds this menu tab
    public Menu(TrackerGUI tracker) {
        this.tracker = tracker;
    }

    public void placeCreateButton() {
        JButton b1 = new JButton("+  Create");
        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.setSize(WIDTH, HEIGHT / 10);

        b1.addActionListener(evt -> createExerciseOrWorkoutDialog());

        this.add(buttonRow);
    }

    //EFFECTS: creates and returns row with button included
    private JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

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

    protected abstract Object[] placeFields();

    protected JTextField getJTextField(Object[] o, int index) {
        return (JTextField) o[index];
    }

    protected abstract void handleFieldInputs(Object[] o);

    //EFFECTS: returns the tracker for this tab
    public TrackerGUI getTracker() {
        return tracker;
    }
}