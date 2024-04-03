package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    private Event testEvent;
    private Date testDate;

    @BeforeEach
    public void runBefore() {
        testEvent = new Event("Exercise added");
        testDate = Calendar.getInstance().getTime();
    }

    @Test
    public void eventTest() {
        assertEquals("Exercise added", testEvent.getDescription());
        assertEquals(testDate, testEvent.getDate());
    }

    @Test
    public void toStringTest() {
        assertEquals(testDate.toString() + "\n" + "Exercise added", testEvent.toString());
    }
}
