package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutTest {

    private Workout testWorkout;
    private Exercise testExercise1;
    private Exercise testExercise2;

    @BeforeEach
    public void runBefore() {
        testWorkout = new Workout(2024, 1, 2, "full body", "ARC");
        testExercise1 = new Exercise("exercise1", "legs", 27.5, 3, 12, 90);
        testExercise2 = new Exercise("exercise2", "full body", 55, 4, 8, 120);
    }

    @Test
    public void constructorTest() {
        assertEquals(2024, testWorkout.getDate().get(0));
        assertEquals(1, testWorkout.getDate().get(1));
        assertEquals(2, testWorkout.getDate().get(2));
        assertEquals("full body", testWorkout.getWorkoutType());
        assertEquals("ARC", testWorkout.getLocation());
        assertEquals(0, testWorkout.getExercises().size());
    }

    //TODO: exceptions testing?
//    @Test
//    public void constructorEmptyStringsTest() {
//        assertEquals(2024, testWorkout.getDate().get(0));
//        assertEquals(1, testWorkout.getDate().get(1));
//        assertEquals(2, testWorkout.getDate().get(2));
//        assertEquals("", testWorkout.getWorkoutType());
//        assertEquals("", testWorkout.getLocation());
//    }

    @Test
    public void addExerciseOnceTest() {
        testWorkout.addExercise(testExercise1);
        assertEquals(1, testWorkout.getExercises().size());
        assertEquals(testExercise1, testWorkout.getExercises().get(0));
    }

    @Test
    public void addExerciseMultipleTest() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        assertEquals(2, testWorkout.getExercises().size());
        assertEquals(testExercise1, testWorkout.getExercises().get(0));
        assertEquals(testExercise2, testWorkout.getExercises().get(1));
    }

    @Test
    public void removeExerciseOnceTest() {
        testWorkout.addExercise(testExercise1);
        assertEquals(1, testWorkout.getExercises().size());
        assertEquals(testExercise1, testWorkout.getExercises().get(0));
        testWorkout.removeExercise(testExercise1);
        assertEquals(0, testWorkout.getExercises().size());
    }

    @Test
    public void removeExerciseMultipleTest() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        assertEquals(2, testWorkout.getExercises().size());
        assertEquals(testExercise1, testWorkout.getExercises().get(0));
        assertEquals(testExercise2, testWorkout.getExercises().get(1));
        testWorkout.removeExercise(testExercise1);
        assertEquals(1, testWorkout.getExercises().size());
        assertEquals(testExercise2, testWorkout.getExercises().get(0));
        testWorkout.removeExercise(testExercise2);
        assertEquals(0, testWorkout.getExercises().size());
    }

    @Test
    public void viewExercisesTest() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        assertEquals("exercise1", testWorkout.viewExercises().get(0));
        assertEquals("exercise2", testWorkout.viewExercises().get(1));
    }

    @Test
    public void viewExercisesWithRemovalTest() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        assertEquals(2, testWorkout.viewExercises().size());
        assertEquals("exercise1", testWorkout.viewExercises().get(0));
        assertEquals("exercise2", testWorkout.viewExercises().get(1));
        testWorkout.removeExercise(testExercise1);
        assertEquals(1, testWorkout.viewExercises().size());
        assertEquals("exercise2", testWorkout.viewExercises().get(0));
    }

    @Test
    public void viewExerciseDetailsTest() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        assertEquals("exercise1 is a(n) legs exercise.\n" +
                "Weight: 27.5lbs\n" +
                "Sets: 3, Reps: 12, Rest time: 90 seconds\n" +
                "Notes: ",
                testWorkout.viewExerciseDetails(testExercise1));
        assertEquals("exercise2 is a(n) full body exercise.\n" +
                "Weight: 55.0lbs\n" +
                "Sets: 4, Reps: 8, Rest time: 120 seconds\n" +
                "Notes: ",
                testWorkout.viewExerciseDetails(testExercise2));
    }

    @Test
    public void viewExerciseDetailsWithEditsTest() {
        testWorkout.addExercise(testExercise2);
        assertEquals("exercise2 is a(n) full body exercise.\n" +
                        "Weight: 55.0lbs\n" +
                        "Sets: 4, Reps: 8, Rest time: 120 seconds\n" +
                        "Notes: ",
                testWorkout.viewExerciseDetails(testExercise2));
        testExercise2.setNote("new note");
        assertEquals("exercise2 is a(n) full body exercise.\n" +
                        "Weight: 55.0lbs\n" +
                        "Sets: 4, Reps: 8, Rest time: 120 seconds\n" +
                        "Notes: new note",
                testWorkout.viewExerciseDetails(testExercise2));
    }

}
