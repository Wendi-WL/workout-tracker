package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventLogTest {
    private Event e1;
    private Event e2;
    private Event e3;

    @BeforeEach
    public void loadEvents() {
        e1 = new Event("Exercise added");
        e2 = new Event("Exercise removed");
        e3 = new Event("Workout added");
        EventLog el = EventLog.getInstance();
        el.logEvent(e1);
        el.logEvent(e2);
        el.logEvent(e3);
    }

    @Test
    public void logEventTest() {
        List<Event> l = new ArrayList<>();

        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }

        assertTrue(l.contains(e1));
        assertTrue(l.contains(e2));
        assertTrue(l.contains(e3));
    }

    @Test
    public void clearTest() {
        EventLog el = EventLog.getInstance();
        el.clear();
        Iterator<Event> itr = el.iterator();
        assertTrue(itr.hasNext());
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }
}
