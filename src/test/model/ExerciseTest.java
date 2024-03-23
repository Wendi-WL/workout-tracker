package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseTest {

    private Exercise testExercise;

    @BeforeEach
    public void runBefore() {
        testExercise = new Exercise("exercise", "legs", 27.5, 3, 12, 90, "");
    }

    @Test
    public void constructorTest() {
        assertEquals("exercise", testExercise.getName());
        assertEquals("legs", testExercise.getExerciseType());
        assertEquals(27.5, testExercise.getWeight());
        assertEquals(3, testExercise.getSets());
        assertEquals(12, testExercise.getReps());
        assertEquals(90, testExercise.getRestTime());
        assertEquals("", testExercise.getNote());
    }

    @Test
    public void exerciseDetailsTest() {
        assertEquals("exercise is a(n) legs exercise.\n" +
                "Weight: 27.5lbs\n" +
                "Sets: 3, Reps: 12, Rest time: 90 seconds\n" +
                "Notes: ",
                testExercise.exerciseDetails());
    }

    @Test
    public void exerciseDetailsAfterNewSettingsTest() {
        testExercise.setName("renamed exercise");
        testExercise.setExerciseType("full body");
        testExercise.setWeight(55);
        testExercise.setSets(4);
        testExercise.setReps(8);
        testExercise.setRestTime(120);
        testExercise.setNote("this is a test note");
        assertEquals("renamed exercise is a(n) full body exercise.\n" +
                "Weight: 55.0lbs\n" +
                "Sets: 4, Reps: 8, Rest time: 120 seconds\n" +
                "Notes: this is a test note",
                testExercise.exerciseDetails());
    }
}
