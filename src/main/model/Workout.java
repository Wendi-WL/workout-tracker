package model;

import java.util.*;

// Represents a workout with details of its date, type, location, and a list of the exercises in the workout
public class Workout {

    private List<Integer> date;
    private String workoutType;
    private String location;
    private final ExerciseList exercises;

    // REQUIRES: year > 0, 1 <= month <= 12, 1 <= day <= 31, workoutType, location are non-empty with no spaces
    // EFFECTS: constructs a new instance of Workout, initialized with parameters and an empty list of exercises
    public Workout(int year, int month, int day, String workoutType, String location) {
        date = new ArrayList<>();
        date.add(year);
        date.add(month);
        date.add(day);
        this.workoutType = workoutType;
        this.location = location;
        exercises = new ExerciseList();
    }

    //getters
    public List<Integer> getDate() {
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
    // MODIFIES: this
    public void setDate(int year, int month, int day) {
        List<Integer> newDate = new ArrayList<>();
        newDate.add(year);
        newDate.add(month);
        newDate.add(day);
        date = newDate;
    }

    // MODIFIES: this
    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    // MODIFIES: this
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
        return getDate().get(0) + "-" + getDate().get(1) + "-" + getDate().get(2);
    }

    // EFFECTS: returns a String of the workout details, not including its exercises
    public String workoutDetails() {
        return formattedDate() + " " + getWorkoutType() + " workout at " + getLocation();
    }

    // EFFECTS: returns the details of and the list of exercises in the workout
    public String workoutDetailsAndExercises() {
        StringBuilder exerciseNames = new StringBuilder();
        for (String s : exercises.listExerciseNames()) {
            exerciseNames.append(s).append(", ");
        }
        exerciseNames = new StringBuilder(exerciseNames.substring(0, exerciseNames.length() - 2));
        return workoutDetails() + "\n" + "Exercises: " + exerciseNames;
    }
}
