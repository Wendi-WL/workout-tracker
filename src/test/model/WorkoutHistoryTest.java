package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutHistoryTest {

    private WorkoutHistory testWorkoutHistory;
    private Workout testWorkout;
    private Workout sameDateWorkout;
    private Workout differentYearWorkout;
    private Workout differentMonthWorkout;
    private Workout differentDayWorkout;

    @BeforeEach
    public void runBefore() {
        testWorkoutHistory = new WorkoutHistory();
        testWorkout = new Workout(2024, 1, 2, "full body", "ARC");
        sameDateWorkout = new Workout(2024, 1, 2, "core", "ARC");
        differentYearWorkout = new Workout(2023, 11, 30, "lower body", "BirdCoop");
        differentMonthWorkout = new Workout(2024, 2, 3, "arms", "ARC");
        differentDayWorkout = new Workout(2024, 1, 1, "legs", "ARC");
    }

    @Test
    public void constructorTest() {
        assertEquals(0, testWorkoutHistory.getWorkouts().size());
    }

    @Test
    public void addWorkoutOnceTest() {
        testWorkoutHistory.addWorkout(testWorkout);
        assertEquals(1, testWorkoutHistory.getWorkouts().size());
        assertEquals(testWorkout, testWorkoutHistory.getWorkouts().get(0));
    }

    @Test
    public void addWorkoutDuplicateTest() {
        testWorkoutHistory.addWorkout(testWorkout);
        testWorkoutHistory.addWorkout(testWorkout);
        assertEquals(1, testWorkoutHistory.getWorkouts().size());
        assertEquals(testWorkout, testWorkoutHistory.getWorkouts().get(0));
    }

    @Test
    public void addWorkoutMultipleTest() {
        testWorkoutHistory.addWorkout(testWorkout);
        testWorkoutHistory.addWorkout(differentYearWorkout);
        assertEquals(2, testWorkoutHistory.getWorkouts().size());
        assertEquals(testWorkout, testWorkoutHistory.getWorkouts().get(0));
        assertEquals(differentYearWorkout, testWorkoutHistory.getWorkouts().get(1));
    }

    @Test
    public void removeWorkoutOnceTest() {
        testWorkoutHistory.addWorkout(testWorkout);
        testWorkoutHistory.addWorkout(differentYearWorkout);
        assertEquals(2, testWorkoutHistory.getWorkouts().size());
        assertEquals(testWorkout, testWorkoutHistory.getWorkouts().get(0));
        assertEquals(differentYearWorkout, testWorkoutHistory.getWorkouts().get(1));
        testWorkoutHistory.removeWorkout(testWorkout);
        assertEquals(1, testWorkoutHistory.getWorkouts().size());
        assertEquals(differentYearWorkout, testWorkoutHistory.getWorkouts().get(0));
    }

    @Test
    public void removeWorkoutMultipleTest() {
        testWorkoutHistory.addWorkout(testWorkout);
        testWorkoutHistory.addWorkout(differentYearWorkout);
        assertEquals(2, testWorkoutHistory.getWorkouts().size());
        assertEquals(testWorkout, testWorkoutHistory.getWorkouts().get(0));
        assertEquals(differentYearWorkout, testWorkoutHistory.getWorkouts().get(1));
        testWorkoutHistory.removeWorkout(testWorkout);
        testWorkoutHistory.removeWorkout(differentYearWorkout);
        assertEquals(0, testWorkoutHistory.getWorkouts().size());
    }

    @Test
    public void listWorkoutsDefaultTest() {
        testWorkoutHistory.addWorkout(testWorkout);
        testWorkoutHistory.addWorkout(differentYearWorkout);
        assertEquals(2, testWorkoutHistory.listWorkoutDetails().size());
        assertEquals("2024-01-02 full body workout at ARC", testWorkoutHistory.listWorkoutDetails().get(0));
        assertEquals("2023-11-30 lower body workout at BirdCoop", testWorkoutHistory.listWorkoutDetails().get(1));
    }

    @Test
    public void sortByDateAlreadySortedTest() {
        testWorkoutHistory.addWorkout(testWorkout);
        testWorkoutHistory.addWorkout(differentYearWorkout);
        assertEquals("2024-01-02 full body workout at ARC", testWorkoutHistory.listWorkoutDetails().get(0));
        assertEquals("2023-11-30 lower body workout at BirdCoop", testWorkoutHistory.listWorkoutDetails().get(1));
        assertEquals("2024-01-02 full body workout at ARC", testWorkoutHistory.sortByDate().get(0));
        assertEquals("2023-11-30 lower body workout at BirdCoop", testWorkoutHistory.sortByDate().get(1));
    }

    @Test
    public void sortByDateSameDateTest() {
        testWorkoutHistory.addWorkout(sameDateWorkout);
        testWorkoutHistory.addWorkout(testWorkout);
        assertEquals("2024-01-02 core workout at ARC", testWorkoutHistory.listWorkoutDetails().get(0));
        assertEquals("2024-01-02 full body workout at ARC", testWorkoutHistory.listWorkoutDetails().get(1));
        assertEquals("2024-01-02 core workout at ARC", testWorkoutHistory.sortByDate().get(0));
        assertEquals("2024-01-02 full body workout at ARC", testWorkoutHistory.sortByDate().get(1));

    }

    @Test
    public void sortByDateDifferentYearTest() {
        testWorkoutHistory.addWorkout(differentYearWorkout);
        testWorkoutHistory.addWorkout(testWorkout);
        assertEquals("2023-11-30 lower body workout at BirdCoop", testWorkoutHistory.listWorkoutDetails().get(0));
        assertEquals("2024-01-02 full body workout at ARC", testWorkoutHistory.listWorkoutDetails().get(1));
        assertEquals("2024-01-02 full body workout at ARC", testWorkoutHistory.sortByDate().get(0));
        assertEquals("2023-11-30 lower body workout at BirdCoop", testWorkoutHistory.sortByDate().get(1));
    }

    @Test
    public void sortByDateDifferentMonthTest() {
        testWorkoutHistory.addWorkout(testWorkout);
        testWorkoutHistory.addWorkout(differentMonthWorkout);
        assertEquals("2024-01-02 full body workout at ARC", testWorkoutHistory.listWorkoutDetails().get(0));
        assertEquals("2024-02-03 arms workout at ARC", testWorkoutHistory.listWorkoutDetails().get(1));
        assertEquals("2024-02-03 arms workout at ARC", testWorkoutHistory.sortByDate().get(0));
        assertEquals("2024-01-02 full body workout at ARC", testWorkoutHistory.sortByDate().get(1));
    }

    @Test
    public void sortByDateDifferentDayTest() {
        testWorkoutHistory.addWorkout(differentDayWorkout);
        testWorkoutHistory.addWorkout(testWorkout);
        assertEquals("2024-01-01 legs workout at ARC", testWorkoutHistory.listWorkoutDetails().get(0));
        assertEquals("2024-01-02 full body workout at ARC", testWorkoutHistory.listWorkoutDetails().get(1));
        assertEquals("2024-01-02 full body workout at ARC", testWorkoutHistory.sortByDate().get(0));
        assertEquals("2024-01-01 legs workout at ARC", testWorkoutHistory.sortByDate().get(1));
    }

    @Test
    public void sortByDateMultipleWorkoutsTest() {
        testWorkoutHistory.addWorkout(differentYearWorkout);
        testWorkoutHistory.addWorkout(differentMonthWorkout);
        testWorkoutHistory.addWorkout(differentDayWorkout);
        testWorkoutHistory.addWorkout(testWorkout);
        assertEquals("2023-11-30 lower body workout at BirdCoop", testWorkoutHistory.listWorkoutDetails().get(0));
        assertEquals("2024-02-03 arms workout at ARC", testWorkoutHistory.listWorkoutDetails().get(1));
        assertEquals("2024-01-01 legs workout at ARC", testWorkoutHistory.listWorkoutDetails().get(2));
        assertEquals("2024-01-02 full body workout at ARC", testWorkoutHistory.listWorkoutDetails().get(3));
        assertEquals("2024-02-03 arms workout at ARC", testWorkoutHistory.sortByDate().get(0));
        assertEquals("2024-01-02 full body workout at ARC", testWorkoutHistory.sortByDate().get(1));
        assertEquals("2024-01-01 legs workout at ARC", testWorkoutHistory.sortByDate().get(2));
        assertEquals("2023-11-30 lower body workout at BirdCoop", testWorkoutHistory.sortByDate().get(3));
    }

    @Test
    public void filterByTypeTest() {
        testWorkoutHistory.addWorkout(testWorkout);
        testWorkoutHistory.addWorkout(differentYearWorkout);
        assertEquals(1, testWorkoutHistory.filterByType("full body").size());
        assertEquals("2024-01-02 full body workout at ARC",
                testWorkoutHistory.filterByType("full body").get(0));
        assertEquals(1, testWorkoutHistory.filterByType("lower body").size());
        assertEquals("2023-11-30 lower body workout at BirdCoop",
                testWorkoutHistory.filterByType("lower body").get(0));
    }

    @Test
    public void filterByTypeNoResultsTest() {
        testWorkoutHistory.addWorkout(testWorkout);
        testWorkoutHistory.addWorkout(differentYearWorkout);
        assertEquals(0, testWorkoutHistory.filterByType("upper body").size());
    }

}
