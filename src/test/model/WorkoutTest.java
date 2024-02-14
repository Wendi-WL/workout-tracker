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
    public void formattedDateTest() {
        assertEquals("2024-1-2", testWorkout.formattedDate());
    }

    @Test
    public void formattedDateAfterNewSettingsTest() {
        testWorkout.setDate(2023,11, 30);
        assertEquals("2023-11-30", testWorkout.formattedDate());
    }

    @Test
    public void workoutDetailsTest() {
        assertEquals("2024-1-2 full body workout at ARC", testWorkout.workoutDetails());
    }

    @Test
    public void workoutDetailsAfterNewSettingsTest() {
        testWorkout.setDate(2023,11, 30);
        testWorkout.setWorkoutType("lower body");
        testWorkout.setLocation("BirdCoop");
        assertEquals("2023-11-30 lower body workout at BirdCoop", testWorkout.workoutDetails());
    }

    @Test
    public void listExercisesTest() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        assertEquals("exercise1", testWorkout.listExercises().get(0));
        assertEquals("exercise2", testWorkout.listExercises().get(1));
    }

    @Test
    public void listExercisesWithRemovalTest() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        assertEquals(2, testWorkout.listExercises().size());
        assertEquals("exercise1", testWorkout.listExercises().get(0));
        assertEquals("exercise2", testWorkout.listExercises().get(1));
        testWorkout.removeExercise(testExercise1);
        assertEquals(1, testWorkout.listExercises().size());
        assertEquals("exercise2", testWorkout.listExercises().get(0));
    }

    @Test
    public void workoutDetailsAndExercisesTest() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        assertEquals("2024-1-2 full body workout at ARC\nExercises: exercise1, exercise2",
                testWorkout.workoutDetailsAndExercises());
    }

    @Test
    public void workoutDetailsAndExercisesWithRemovalTest() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        assertEquals("2024-1-2 full body workout at ARC\nExercises: exercise1, exercise2",
                testWorkout.workoutDetailsAndExercises());
        testWorkout.removeExercise(testExercise1);
        assertEquals("2024-1-2 full body workout at ARC\nExercises: exercise2",
                testWorkout.workoutDetailsAndExercises());
    }

}
