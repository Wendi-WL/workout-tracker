package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

// Represents a workout with details of its date, type, location, and a list of the exercises in the workout
public class Workout {

    private LocalDate date;
    private String workoutType;
    private String location;
    private final ExerciseList exercises;

    // REQUIRES: year, month, day make up a valid date
    // EFFECTS: constructs a new instance of Workout, initialized with parameters and an empty list of exercises
    public Workout(int year, int month, int day, String workoutType, String location) {
        date = LocalDate.of(year, month, day);
        this.workoutType = workoutType;
        this.location = location;
        exercises = new ExerciseList();
    }

    //getters
    public LocalDate getDate() {
        return date;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public String getLocation() {
        return location;
    }

    public ExerciseList getExercises() {
        return exercises;
    }

    //setters
    public void setDate(int year, int month, int day) {
        date = LocalDate.of(year, month, day);
    }

    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // MODIFIES: this
    // EFFECTS: adds the exercise to the list of exercises in this workout
    public void addExercise(Exercise e) {
        exercises.create(e);
    }

    // REQUIRES: e is in exercises
    // MODIFIES: this
    // EFFECTS: removes the exercise from the list of exercises in this workout
    public void removeExercise(Exercise e) {
        exercises.delete(e);
    }

    // EFFECTS: returns a String of a formatted date
    public String formattedDate() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // EFFECTS: returns a String of the workout details, not including its exercises
    public String workoutDetails() {
        return formattedDate() + " " + getWorkoutType() + " workout at " + getLocation();
    }

    public String workoutExercises() {
        if (exercises.getExerciseList().isEmpty()) {
            return "";
        }
        StringBuilder exerciseNames = new StringBuilder();
        for (String s : exercises.listExerciseNames()) {
            exerciseNames.append(s).append(", ");
        }
        return exerciseNames.substring(0, exerciseNames.length() - 2);
    }

    // EFFECTS: returns the details of and the list of exercises in the workout
    public String workoutDetailsAndExercises() {
        return workoutDetails() + "\n" + "Exercises: " + workoutExercises();
    }

    // EFFECTS: returns workout as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", dateToJson());
        json.put("workout type", workoutType);
        json.put("location", location);
        json.put("exercises", exercises.exercisesToJson());
        return json;
    }

    // EFFECTS: returns date as JSONArray
    private JSONArray dateToJson() {
        JSONArray dateArray = new JSONArray();
        dateArray.put(date.getYear());
        dateArray.put(date.getMonthValue());
        dateArray.put(date.getDayOfMonth());
        return dateArray;
    }

    // EFFECTS: returns true if date, workout type, and location fields are equivalent between two Workout objects
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Workout workout = (Workout) o;
        return Objects.equals(date, workout.date) && Objects.equals(workoutType, workout.workoutType)
                && Objects.equals(location, workout.location) && Objects.equals(exercises, workout.exercises);
    }

    // EFFECTS: overridden hashCode based on overridden equals
    @Override
    public int hashCode() {
        return Objects.hash(date, workoutType, location, exercises);
    }
}
