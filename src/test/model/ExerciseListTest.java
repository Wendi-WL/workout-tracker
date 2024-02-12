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

}
