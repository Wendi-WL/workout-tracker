package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataWriterTest extends DataCheckerTest {
    private ExerciseList el;
    private WorkoutHistory wh;
    private DataWriter writer;
    private DataReader reader;

    @BeforeEach
    public void runBefore() {
        el = new ExerciseList();
        wh = new WorkoutHistory();
    }

    @Test
    public void writeInvalidFileTest() {
        try {
            writer = new DataWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //good
        }
    }

    @Test
    void writeEmptyFileTest() {
        try {
            writer = new DataWriter("./data/writerEmptyTest.json");
            writer.open();
            writer.write(el, wh);
            writer.close();
            reader = new DataReader("./data/writerEmptyTest.json");
            el = reader.readExerciseList();
            wh = reader.readWorkoutHistory();
            assertEquals(0, el.getExerciseList().size());
            assertEquals(0, wh.getWorkouts().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void writeGeneralFileTest() {
        try {
            Exercise e1 = new Exercise("e1", "legs", 27.5, 3, 12, 60, "sample note");
            Exercise e2 = new Exercise("e2", "arms", 12.5, 5, 8, 30, "another sample note");
            el.create(e1);
            el.create(e2);

            Workout w1 = new Workout(2023, 2, 3, "lower body", "BirdCoop");
            w1.addExercise(e1);
            ExerciseList w1E = new ExerciseList();
            w1E.create(e1);
            Workout w2 = new Workout(2024, 1, 1, "full body", "ARC");
            w2.addExercise(e1);
            w2.addExercise(e2);
            ExerciseList w2E = new ExerciseList();
            w2E.create(e1);
            w2E.create(e2);
            wh.addWorkout(w1);
            wh.addWorkout(w2);

            writer = new DataWriter("./data/writerGeneralTest.json");
            writer.open();
            writer.write(el, wh);
            writer.close();
            reader = new DataReader("./data/writerGeneralTest.json");
            el = reader.readExerciseList();
            wh = reader.readWorkoutHistory();
            List<Exercise> exercises = el.getExerciseList();
            List<Workout> workouts = wh.getWorkouts();

            assertEquals(2, exercises.size());
            assertEquals(2, workouts.size());

            checkExercise("e1", "legs", 27.5, 3, 12, 60,
                    "sample note", exercises.get(0));
            checkExercise("e2", "arms", 12.5, 5, 8, 30,
                    "another sample note", exercises.get(1));

            checkWorkout(2023, 2, 3, "lower body", "BirdCoop", workouts.get(0));
            assertEquals(1, workouts.get(0).getExercises().getExerciseList().size());
            checkExercise("e1", "legs", 27.5, 3, 12, 60,
                    "sample note", workouts.get(0).getExercises().getExerciseList().get(0));
            checkWorkout(2024, 1, 1, "full body", "ARC", workouts.get(1));
            assertEquals(2, workouts.get(1).getExercises().getExerciseList().size());
            checkExercise("e1", "legs", 27.5, 3, 12, 60,
                    "sample note", workouts.get(1).getExercises().getExerciseList().get(0));
            checkExercise("e2", "arms", 12.5, 5, 8, 30,
                    "another sample note", workouts.get(1).getExercises().getExerciseList().get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}