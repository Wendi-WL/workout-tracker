package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataCheckerTest {
    protected void checkExercise(String name, String exerciseType, double weight, int sets, int reps, int restTime,
                                 String note, Exercise e) {
        assertEquals(name, e.getName());
        assertEquals(exerciseType, e.getExerciseType());
        assertEquals(weight, e.getWeight());
        assertEquals(sets, e.getSets());
        assertEquals(reps, e.getReps());
        assertEquals(restTime, e.getRestTime());
        assertEquals(note, e.getNote());
    }

    protected void checkWorkout(int year, int month, int day, String workoutType, String location,
                                Workout w) {
        assertEquals(year, w.getDate().get(0));
        assertEquals(month, w.getDate().get(1));
        assertEquals(day, w.getDate().get(2));
        assertEquals(workoutType, w.getWorkoutType());
        assertEquals(location, w.getLocation());
    }
}

