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
        assertEquals(year, w.getDate().getYear());
        assertEquals(month, w.getDate().getMonthValue());
        assertEquals(day, w.getDate().getDayOfMonth());
        assertEquals(workoutType, w.getWorkoutType());
        assertEquals(location, w.getLocation());
    }
}

