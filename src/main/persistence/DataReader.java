package persistence;

import model.*;

import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads the exercise list and the workout history from JSON data stored in file
public class DataReader {
    private final String sourceFile;

    // EFFECTS: constructs reader to read from source file
    public DataReader(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    // EFFECTS: reads data from file and returns it; throws IOException if an error occurs reading data from file
    private JSONObject readData() throws IOException {
        String jsonData = readFile(sourceFile);
        return new JSONObject(jsonData);
    }

    // EFFECTS: reads exercise list from file and returns it; throws IOException if an error occurs
    public ExerciseList readExerciseList() throws IOException {
        return parseExerciseList(readData());
    }

    // EFFECTS: reads workout history from file and returns it; throws IOException if an error occurs
    public WorkoutHistory readWorkoutHistory() throws IOException {
        return parseWorkoutHistory(readData());
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses exercise list from JSON object and returns it
    private ExerciseList parseExerciseList(JSONObject jsonObject) {
        ExerciseList el = new ExerciseList();
        addExercises(el, jsonObject);
        return el;
    }

    // EFFECTS: parses workout history from JSON object and returns it
    private WorkoutHistory parseWorkoutHistory(JSONObject jsonObject) {
        WorkoutHistory wh = new WorkoutHistory();
        addWorkouts(wh, jsonObject);
        return wh;
    }

    // MODIFIES: el
    // EFFECTS: parses exercises from JSON array and adds them to exercise list
    private void addExercises(ExerciseList el, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("exercises");
        for (Object json : jsonArray) {
            JSONObject nextExercise = (JSONObject) json;
            addExercise(el, nextExercise);
        }
    }

    // MODIFIES: w
    // EFFECTS: parses exercises from JSON array and adds them to the workout's exercises
    private void addExercises(Workout w, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("exercises");
        for (Object json : jsonArray) {
            JSONObject nextExercise = (JSONObject) json;
            addExercise(w, nextExercise);
        }
    }

    // MODIFIES: wh
    // EFFECTS: parses workouts from JSON array and adds them to workout history
    private void addWorkouts(WorkoutHistory wh, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("workouts");
        for (Object json : jsonArray) {
            JSONObject nextWorkout = (JSONObject) json;
            addWorkout(wh, nextWorkout);
        }
    }

    // MODIFIES: el
    // EFFECTS: parses exercise from JSON object and adds it to exercise list
    private void addExercise(ExerciseList el, JSONObject jsonObject) {
        el.create(parseExercise(jsonObject));
    }

    // MODIFIES: w
    // EFFECTS: parses exercise from JSON object and adds it to the workout's exercises
    private void addExercise(Workout w, JSONObject jsonObject) {
        w.addExercise(parseExercise(jsonObject));
    }

    // EFFECTS: parses exercise from JSON object and returns it
    private Exercise parseExercise(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String exerciseType = jsonObject.getString("exercise type");
        double weight = jsonObject.getDouble("weight");
        int sets = jsonObject.getInt("sets");
        int reps = jsonObject.getInt("reps");
        int restTime = jsonObject.getInt("rest time");
        String note = jsonObject.getString("note");

        return new Exercise(name, exerciseType, weight, sets, reps, restTime, note);
    }

    // MODIFIES: wh
    // EFFECTS: parses workout from JSON array and adds it to workout history
    private void addWorkout(WorkoutHistory wh, JSONObject jsonObject) {
        JSONArray date = jsonObject.getJSONArray("date");
        int year = date.getInt(0);
        int month = date.getInt(1);
        int day = date.getInt(2);
        String workoutType = jsonObject.getString("workout type");
        String location = jsonObject.getString("location");
        Workout w = new Workout(year, month, day, workoutType, location);

        addExercises(w, jsonObject);

        wh.addWorkout(w);
    }
}