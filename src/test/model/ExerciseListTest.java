package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseListTest {

    private ExerciseList testExerciseList;
    private Exercise testExercise1;
    private Exercise testExercise2;

    @BeforeEach
    public void runBefore() {
        testExerciseList = new ExerciseList();
        testExercise1 = new Exercise("exercise1", "legs", 27.5, 3, 12, 90);
        testExercise2 = new Exercise("exercise2", "full body", 55, 4, 8, 120);
    }

    @Test
    public void constructorTest() {
        assertEquals(0, testExerciseList.getExerciseList().size());
    }

    @Test
    public void createOnceTest() {
        testExerciseList.create(testExercise1);
        assertEquals(1, testExerciseList.getExerciseList().size());
        assertEquals(testExercise1, testExerciseList.getExerciseList().get(0));
    }

    @Test
    public void createMultipleTest() {
        testExerciseList.create(testExercise1);
        testExerciseList.create(testExercise2);
        assertEquals(2, testExerciseList.getExerciseList().size());
        assertEquals(testExercise1, testExerciseList.getExerciseList().get(0));
        assertEquals(testExercise2, testExerciseList.getExerciseList().get(1));
    }

    @Test
    public void deleteOnceTest() {
        testExerciseList.create(testExercise1);
        assertEquals(1, testExerciseList.getExerciseList().size());
        assertEquals(testExercise1, testExerciseList.getExerciseList().get(0));
        testExerciseList.delete(testExercise1);
        assertEquals(0, testExerciseList.getExerciseList().size());
    }

    @Test
    public void deleteMultipleTest() {
        testExerciseList.create(testExercise1);
        testExerciseList.create(testExercise2);
        assertEquals(2, testExerciseList.getExerciseList().size());
        assertEquals(testExercise1, testExerciseList.getExerciseList().get(0));
        assertEquals(testExercise2, testExerciseList.getExerciseList().get(1));
        testExerciseList.delete(testExercise1);
        assertEquals(1, testExerciseList.getExerciseList().size());
        assertEquals(testExercise2, testExerciseList.getExerciseList().get(0));
        testExerciseList.delete(testExercise2);
        assertEquals(0, testExerciseList.getExerciseList().size());
    }

    @Test
    public void exerciseDetailsTest() {
        testExerciseList.create(testExercise1);
        testExerciseList.create(testExercise2);
        assertEquals("exercise1 is a(n) legs exercise.\n" +
                        "Weight: 27.5lbs\n" +
                        "Sets: 3, Reps: 12, Rest time: 90 seconds\n" +
                        "Notes: ",
                testExerciseList.exerciseDetails(testExercise1));
        assertEquals("exercise2 is a(n) full body exercise.\n" +
                        "Weight: 55.0lbs\n" +
                        "Sets: 4, Reps: 8, Rest time: 120 seconds\n" +
                        "Notes: ",
                testExerciseList.exerciseDetails(testExercise2));
    }

    @Test
    public void exerciseDetailsWithEditsTest() {
        testExerciseList.create(testExercise2);
        assertEquals("exercise2 is a(n) full body exercise.\n" +
                        "Weight: 55.0lbs\n" +
                        "Sets: 4, Reps: 8, Rest time: 120 seconds\n" +
                        "Notes: ",
                testExerciseList.exerciseDetails(testExercise2));
        testExercise2.setNote("new note");
        assertEquals("exercise2 is a(n) full body exercise.\n" +
                        "Weight: 55.0lbs\n" +
                        "Sets: 4, Reps: 8, Rest time: 120 seconds\n" +
                        "Notes: new note",
                testExerciseList.exerciseDetails(testExercise2));
    }

    @Test
    public void listExercisesTest() {
        testExerciseList.create(testExercise1);
        testExerciseList.create(testExercise2);
        assertEquals("exercise1", testExerciseList.listExerciseNames().get(0));
        assertEquals("exercise2", testExerciseList.listExerciseNames().get(1));
    }

    @Test
    public void listExercisesWithRemovalTest() {
        testExerciseList.create(testExercise1);
        testExerciseList.create(testExercise2);
        assertEquals(2, testExerciseList.listExerciseNames().size());
        assertEquals("exercise1", testExerciseList.listExerciseNames().get(0));
        assertEquals("exercise2", testExerciseList.listExerciseNames().get(1));
        testExerciseList.delete(testExercise1);
        assertEquals(1, testExerciseList.listExerciseNames().size());
        assertEquals("exercise2", testExerciseList.listExerciseNames().get(0));
    }

    @Test
    public void filterByTypeTest() {
        testExerciseList.create(testExercise1);
        testExerciseList.create(testExercise2);
        assertEquals(1, testExerciseList.filterByType("full body").size());
        assertEquals("exercise2", testExerciseList.filterByType("full body").get(0));
        assertEquals(1, testExerciseList.filterByType("legs").size());
        assertEquals("exercise1", testExerciseList.filterByType("legs").get(0));
    }

    @Test
    public void filterByTypeNoResultsTest() {
        testExerciseList.create(testExercise1);
        testExerciseList.create(testExercise2);
        assertEquals(0, testExerciseList.filterByType("lower body").size());
    }

}
