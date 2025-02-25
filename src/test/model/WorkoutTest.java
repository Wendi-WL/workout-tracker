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
        testExercise1 = new Exercise("exercise1", "legs", 27.5, 3, 12, 90, "");
        testExercise2 = new Exercise("exercise2", "full body", 55, 4, 8, 120, "");
    }

    @Test
    public void constructorTest() {
        assertEquals(2024, testWorkout.getDate().getYear());
        assertEquals(1, testWorkout.getDate().getMonthValue());
        assertEquals(2, testWorkout.getDate().getDayOfMonth());
        assertEquals("full body", testWorkout.getWorkoutType());
        assertEquals("ARC", testWorkout.getLocation());
        assertEquals(0, testWorkout.getExercises().getExerciseList().size());
    }

    @Test
    public void addExerciseOnceTest() {
        testWorkout.addExercise(testExercise1);
        assertEquals(1, testWorkout.getExercises().getExerciseList().size());
        assertEquals(testExercise1, testWorkout.getExercises().getExerciseList().get(0));
    }

    @Test
    public void addExerciseMultipleTest() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        assertEquals(2, testWorkout.getExercises().getExerciseList().size());
        assertEquals(testExercise1, testWorkout.getExercises().getExerciseList().get(0));
        assertEquals(testExercise2, testWorkout.getExercises().getExerciseList().get(1));
    }

    @Test
    public void removeExerciseOnceTest() {
        testWorkout.addExercise(testExercise1);
        assertEquals(1, testWorkout.getExercises().getExerciseList().size());
        assertEquals(testExercise1, testWorkout.getExercises().getExerciseList().get(0));
        testWorkout.removeExercise(testExercise1);
        assertEquals(0, testWorkout.getExercises().getExerciseList().size());
    }

    @Test
    public void removeExerciseMultipleTest() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        assertEquals(2, testWorkout.getExercises().getExerciseList().size());
        assertEquals(testExercise1, testWorkout.getExercises().getExerciseList().get(0));
        assertEquals(testExercise2, testWorkout.getExercises().getExerciseList().get(1));
        testWorkout.removeExercise(testExercise1);
        assertEquals(1, testWorkout.getExercises().getExerciseList().size());
        assertEquals(testExercise2, testWorkout.getExercises().getExerciseList().get(0));
        testWorkout.removeExercise(testExercise2);
        assertEquals(0, testWorkout.getExercises().getExerciseList().size());
    }

    @Test
    public void formattedDateTest() {
        assertEquals("2024-01-02", testWorkout.formattedDate());
    }

    @Test
    public void formattedDateAfterNewSettingsTest() {
        testWorkout.setDate(2023,11, 30);
        assertEquals("2023-11-30", testWorkout.formattedDate());
    }

    @Test
    public void workoutDetailsTest() {
        assertEquals("2024-01-02 full body workout at ARC", testWorkout.workoutDetails());
    }

    @Test
    public void workoutDetailsAfterNewSettingsTest() {
        testWorkout.setDate(2023,11, 30);
        testWorkout.setWorkoutType("lower body");
        testWorkout.setLocation("BirdCoop");
        assertEquals("2023-11-30 lower body workout at BirdCoop", testWorkout.workoutDetails());
    }

    @Test
    public void workoutDetailsAndExercisesTest() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        assertEquals("2024-01-02 full body workout at ARC\nExercises: exercise1, exercise2",
                testWorkout.workoutDetailsAndExercises());
    }

    @Test
    public void workoutDetailsAndExercisesWithRemovalTest() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        assertEquals("2024-01-02 full body workout at ARC\nExercises: exercise1, exercise2",
                testWorkout.workoutDetailsAndExercises());
        testWorkout.removeExercise(testExercise1);
        assertEquals("2024-01-02 full body workout at ARC\nExercises: exercise2",
                testWorkout.workoutDetailsAndExercises());
    }

    @Test
    public void workoutDetailsAndExercisesEmptyExercisesTest() {
        assertEquals("2024-01-02 full body workout at ARC\nExercises: ",
                testWorkout.workoutDetailsAndExercises());
    }

    @Test
    public void equalsTest() {
        Workout testWorkoutEquals = new Workout(2024, 1, 2, "full body", "ARC");
        assertEquals(testWorkout, testWorkoutEquals);
    }

    @Test
    public void equalsSameObjectTest() {
        Workout testWorkoutSameObject = testWorkout;
        assertEquals(testWorkout, testWorkoutSameObject);
    }

    @Test
    public void equalsToNullTest() {
        assertNotEquals(testWorkout, null);
    }

    @Test
    public void equalsToDifferentClassTest() {
        assertNotEquals(testWorkout, testExercise1);
    }

    @Test
    public void equalsDiffDateTest() {
        Workout testWorkoutDiffDate = new Workout(2024, 2, 2, "full body", "ARC");
        assertNotEquals(testWorkout, testWorkoutDiffDate);
    }

    @Test
    public void equalsDiffTypeTest() {
        Workout testWorkoutDiffType = new Workout(2024, 1, 2, "upper body", "ARC");
        assertNotEquals(testWorkout, testWorkoutDiffType);
    }

    @Test
    public void equalsDiffLocationTest() {
        Workout testWorkoutDiffLocation = new Workout(2024, 1, 2, "full body", "BirdCoop");
        assertNotEquals(testWorkout, testWorkoutDiffLocation);
    }

    @Test
    void hashCodeTest() {
        Workout testWorkoutEquals = new Workout(2024, 1, 2, "full body", "ARC");
        assertEquals(testWorkout.hashCode(), testWorkoutEquals.hashCode());
        Workout testWorkoutNotEquals = new Workout(2023, 1, 1, "arms", "BirdCoop");
        assertNotEquals(testWorkout.hashCode(), testWorkoutNotEquals.hashCode());
    }

}
