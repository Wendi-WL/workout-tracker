package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

    @Test
    public void equalsTest() {
        Event testEventEquals = new Event("Exercise added");
        assertEquals(testEvent, testEventEquals);
    }

    @Test
    public void equalsSameObjectTest() {
        Event testEventSameObject = testEvent;
        assertEquals(testEvent, testEventSameObject);
    }

    @Test
    public void equalsToNullTest() {
        assertNotEquals(testEvent, null);
    }

    @Test
    public void equalsToDifferentClassTest() {
        assertNotEquals(testEvent, testDate);
    }

    @Test
    public void equalsDiffDescriptionTest() {
        Event testEventDiffDescription = new Event("Exercise removed");
        assertNotEquals(testEvent, testEventDiffDescription);
    }

    @Test
    void hashCodeTest() {
        Event testEventEquals = new Event("Exercise added");
        assertEquals(testEvent.hashCode(), testEventEquals.hashCode());
        Event testEventNotEquals = new Event("Exercise removed");
        assertNotEquals(testEvent.hashCode(), testEventNotEquals.hashCode());
    }
}
