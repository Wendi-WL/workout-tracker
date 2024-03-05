package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataReaderTest extends DataCheckerTest {
    private ExerciseList el;
    private WorkoutHistory wh;
    private DataReader reader;

    @Test
    void readNonExistentFileTest() {
        reader = new DataReader("./data/nonexistent.json");
        try {
            el = reader.readExerciseList();
            wh = reader.readWorkoutHistory();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void readEmptyFileTest() {
        reader = new DataReader("./data/readerEmptyTest.json");
        try {
            el = reader.readExerciseList();
            wh = reader.readWorkoutHistory();
            assertEquals(0, el.getExerciseList().size());
            assertEquals(0, wh.getWorkouts().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void readGeneralFileTest() {
        reader = new DataReader("./data/readerGeneralTest.json");
        try {
            el = reader.readExerciseList();
            wh = reader.readWorkoutHistory();
            List<Exercise> exercises = el.getExerciseList();
            List<Workout> workouts = wh.getWorkouts();

            assertEquals(3, exercises.size());
            assertEquals(1, workouts.size());

            checkExercise("e1", "legs", 27.5, 3, 12, 60,
                    "sample note", exercises.get(0));
            checkExercise("e2", "arms", 12.5, 5, 8, 30,
                    "another sample note", exercises.get(1));
            checkExercise("e3", "core", 0, 4, 15, 60,
                    "", exercises.get(2));

            checkWorkout(2023, 12, 31, "upper body", "BirdCoop", workouts.get(0));
            assertEquals(2, workouts.get(0).getExercises().getExerciseList().size());
            checkExercise("e2", "arms", 12.5, 5, 8, 30,
                    "another sample note", workouts.get(0).getExercises().getExerciseList().get(0));
            checkExercise("e3", "core", 0, 4, 15, 60,
                    "", workouts.get(0).getExercises().getExerciseList().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
