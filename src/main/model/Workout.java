package model;

import java.util.*;

// Represents a workout consisting of exercises, and with details of its date, type, and location
public class Workout {

    private List<Integer> date;
    private String workoutType;
    private String location;
    private List<Exercise> exercises;

    // REQUIRES: year > 0, 1 <= month <= 12, 1 <= day <= 31
    // EFFECTS: constructs a new instance of Workout, initialized with parameters and an empty list of exercises
    public Workout(int year, int month, int day, String workoutType, String location) {
        date = new ArrayList<>();
        date.add(year);
        date.add(month);
        date.add(day);
        this.workoutType = workoutType;
        this.location = location;
        exercises = new ArrayList<>();
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

    public List<Exercise> getExercises() {
        return exercises;
    }

    //setters
    public void setDate(int year, int month, int day) {
        List<Integer> newDate = new ArrayList<>();
        newDate.add(year);
        newDate.add(month);
        newDate.add(day);
        date = newDate;
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
        exercises.add(e);
    }

    // REQUIRES: e is in exercises
    // MODIFIES: this
    // EFFECTS: removes the exercise from the list of exercises in this workout
    public void removeExercise(Exercise e) {
        exercises.remove(e);
    }

    // EFFECTS: returns a list of names of the exercises in the workout
    public List<String> viewExercises() {
        List<String> exerciseNames = new ArrayList<>();
        for (Exercise e : exercises) {
            exerciseNames.add(e.getName());
        }
        return exerciseNames;
    }

    // REQUIRES: e is in exercises
    // EFFECTS: returns a String of the details of the selected exercise
    public String viewExerciseDetails(Exercise e) {
        return e.exerciseDetails();
    }
}
