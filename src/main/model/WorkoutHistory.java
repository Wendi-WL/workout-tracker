package model;

import java.util.*;

// Represents a workout history of past workouts
public class WorkoutHistory {

    private List<Workout> workouts;

    // EFFECTS: constructs a new instance of WorkoutHistory, initialized as an empty list of workouts
    public WorkoutHistory() {
        workouts = new ArrayList<Workout>();
    }

    //getter
    public List<Workout> getWorkouts() {
        return workouts;
    }

    // MODIFIES: this
    // EFFECTS: adds the workout to the workout history
    public void addWorkout(Workout w) {
        workouts.add(w);
    }

    // REQUIRES: w is in workouts
    // MODIFIES: this
    // EFFECTS: removes the workout from the workout history
    public void removeWorkout(Workout w) {
        workouts.remove(w);
    }
}
