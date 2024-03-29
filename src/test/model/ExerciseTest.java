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

    @Test
    public void equalsTest() {
        Exercise testExerciseEquals = new Exercise("exercise", "legs", 27.5, 3, 12, 90, "");
        assertEquals(testExercise, testExerciseEquals);
    }

    @Test
    public void equalsSameObjectTest() {
        Exercise testExerciseSameObject = testExercise;
        assertEquals(testExercise, testExerciseSameObject);
    }

    @Test
    public void equalsToNullTest() {
        assertNotEquals(testExercise, null);
    }

    @Test
    public void equalsToDifferentClassTest() {
        Workout testWorkout = new Workout(2024, 1, 1, "type", "location");
        assertNotEquals(testExercise, testWorkout);
    }

    @Test
    public void equalsDiffNameTest() {
        Exercise testExerciseDiffName = new Exercise("exercise 2", "legs", 27.5, 3, 12, 90, "");
        assertNotEquals(testExercise, testExerciseDiffName);
    }

    @Test
    public void equalsDiffTypeTest() {
        Exercise testExerciseDiffType = new Exercise("exercise", "arms", 27.5, 3, 12, 90, "");
        assertNotEquals(testExercise, testExerciseDiffType);
    }

    @Test
    public void equalsDiffWeightTest() {
        Exercise testExerciseDiffWeight = new Exercise("exercise", "legs", 12, 3, 12, 90, "");
        assertNotEquals(testExercise, testExerciseDiffWeight);
    }

    @Test
    public void equalsDiffSetsTest() {
        Exercise testExerciseDiffSets = new Exercise("exercise", "legs", 27.5, 4, 12, 90, "");
        assertNotEquals(testExercise, testExerciseDiffSets);
    }

    @Test
    public void equalsDiffRepsTest() {
        Exercise testExerciseDiffReps = new Exercise("exercise", "legs", 27.5, 3, 10, 90, "");
        assertNotEquals(testExercise, testExerciseDiffReps);
    }

    @Test
    public void equalsDiffRestTimeTest() {
        Exercise testExerciseDiffRestTime = new Exercise("exercise", "legs", 27.5, 3, 12, 60, "");
        assertNotEquals(testExercise, testExerciseDiffRestTime);
    }

    @Test
    public void equalsDiffNoteTest() {
        Exercise testExerciseDiffNote = new Exercise("exercise", "legs", 27.5, 3, 12, 90, "note");
        assertNotEquals(testExercise, testExerciseDiffNote);
    }

    @Test
    void hashCodeTest() {
        Exercise testExerciseEquals = new Exercise("exercise", "legs", 27.5, 3, 12, 90, "");
        assertEquals(testExercise.hashCode(), testExerciseEquals.hashCode());
        Exercise testExerciseNotEquals = new Exercise("not exercise", "legs", 27.5, 3, 12, 90, "");
        assertNotEquals(testExercise.hashCode(), testExerciseNotEquals.hashCode());
    }
}
