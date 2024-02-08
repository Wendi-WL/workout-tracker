package model;

import java.util.*;

// Represents a workout consisting of exercises, and with details of its date, type, and location
public class Workout {

    private List<Integer> date;
    private String workoutType;
    private String location;
    private List<Exercise> exercises;

    // EFFECTS: constructs a new instance of Workout, initialized with parameters and an empty list of exercises
    public Workout(int month, int day, int year, String workoutType, String location) {
        date = new ArrayList<Integer>();
        date.add(month);
        date.add(day);
        date.add(year);
        this.workoutType = workoutType;
        this.location = location;
        exercises = new ArrayList<Exercise>();
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
    public void setDate(int month, int day, int year) {
        List<Integer> newDate = new ArrayList<Integer>();
        newDate.add(month);
        newDate.add(day);
        newDate.add(year);
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
}
