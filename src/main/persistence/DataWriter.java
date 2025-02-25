package persistence;

import model.ExerciseList;
import model.WorkoutHistory;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes a representation of the exercise list and the workout history to a JSON file
public class DataWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private final String destinationFile;

    // EFFECTS: constructs writer to write to destination file
    public DataWriter(String destinationFile) {
        this.destinationFile = destinationFile;
    }

    // MODIFIES: this
    // EFFECTS: opens writer, throwing FileNotFoundException if destination cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destinationFile);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of exercises and workouts to destination file
    public void write(ExerciseList el, WorkoutHistory wh) {
        JSONObject json = new JSONObject();
        json.put("exercises", el.exercisesToJson());
        json.put("workouts", wh.workoutsToJson());
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}