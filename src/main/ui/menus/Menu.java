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

    //EFFECTS: creates and returns row with button included
    public JPanel formatButton(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    //EFFECTS: returns the SmartHomeUI controller for this tab
    public TrackerGUI getTracker() {
        return tracker;
    }
}