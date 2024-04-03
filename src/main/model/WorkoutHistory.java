package model;

import org.json.JSONArray;

import java.util.*;

// Represents a workout history of past workouts
public class WorkoutHistory {

    private final List<Workout> workouts;

    // EFFECTS: constructs a new instance of WorkoutHistory, initialized as an empty list of workouts
    public WorkoutHistory() {
        workouts = new ArrayList<>();
    }

    //getter
    public List<Workout> getWorkouts() {
        return workouts;
    }

    // MODIFIES: this
    // EFFECTS: adds the workout to the workout history, if there is no existing workout with same date, type, location
    public void addWorkout(Workout w) {
        if (!workouts.contains(w)) {
            workouts.add(w);
        }
        EventLog.getInstance().logEvent(new Event("Workout: " + w.workoutDetails()
                + " was added to the workout history"));
    }

    // REQUIRES: w is in workouts
    // MODIFIES: this
    // EFFECTS: removes the workout from the workout history
    public void removeWorkout(Workout w) {
        workouts.remove(w);
        EventLog.getInstance().logEvent(new Event("Workout: " + w.workoutDetails()
                + " was removed from the workout history"));
    }

    // EFFECTS: returns a list of specified workouts' date, type, and location details
    public List<String> listWorkoutDetails(List<Workout> workoutList) {
        List<String> workoutsDetailsList = new ArrayList<>();
        for (Workout w : workoutList) {
            workoutsDetailsList.add(w.workoutDetails());
        }
        return workoutsDetailsList;
    }

    // EFFECTS: returns a list of workouts' date, type, and location details for all workouts in the workout history
    public List<String> listWorkoutDetails() {
        return listWorkoutDetails(workouts);
    }

    // MODIFIES: this
    // EFFECTS: returns reordered list of workouts by date, with most recent first
    public List<String> sortByDate() {
        List<Workout> sortedWorkouts = new ArrayList<>();
        sortedWorkouts.add(workouts.get(0));
        List<Workout> remainingWorkouts = workouts.subList(1, workouts.size());
        for (Workout w : remainingWorkouts) {
            int position = 0;
            for (Workout sw : sortedWorkouts) {
                if (w.getDate().isAfter(sw.getDate())) {
                    break;
                } else {
                    position++;
                }
            }
            sortedWorkouts.add(position, w);
        }
        return listWorkoutDetails(sortedWorkouts);
    }

    // MODIFIES: this
    // EFFECTS: returns filtered list of workouts, filtering by given workoutType
    public List<String> filterByType(String workoutType) {
        List<Workout> filteredWorkouts = new ArrayList<>();
        for (Workout w : workouts) {
            if (w.getWorkoutType().equals(workoutType)) {
                filteredWorkouts.add(w);
            }
        }
        return listWorkoutDetails(filteredWorkouts);
    }

    // EFFECTS: returns workouts in the workout history as a JSON array
    public JSONArray workoutsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Workout w : workouts) {
            jsonArray.put(w.toJson());
        }
        return jsonArray;
    }
}
