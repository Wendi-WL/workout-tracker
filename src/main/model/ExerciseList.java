package model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

// Represents a list that stores all created exercises
public class ExerciseList {

    private final List<Exercise> exerciseList;

    // EFFECTS: constructs a new instance of ExerciseList, initialized as an empty list of exercises
    public ExerciseList() {
        exerciseList = new ArrayList<>();
    }

    //getter
    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    // MODIFIES: this
    // EFFECTS: adds the exercise to the list of all created exercises and logs event
    public void create(Exercise e) {
        exerciseList.add(e);
        EventLog.getInstance().logEvent(new Event("Exercise: " + e.getName() + " was added to the exercise list"));
    }

    // REQUIRES: e is in exerciseList
    // MODIFIES: this
    // EFFECTS: removes the exercise from the list of all created exercises and logs event
    public void delete(Exercise e) {
        exerciseList.remove(e);
        EventLog.getInstance().logEvent(new Event("Exercise: " + e.getName() + " was removed from the exercise list"));
    }

    // REQUIRES: e is in exerciseList
    // EFFECTS: returns a String of the details of the selected exercise
    public String exerciseDetails(Exercise e) {
        return e.exerciseDetails();
    }

    // EFFECTS: returns a list of names of the exercises in le
    public List<String> listExerciseNames(List<Exercise> le) {
        List<String> exerciseNames = new ArrayList<>();
        for (Exercise e : le) {
            exerciseNames.add(e.getName());
        }
        return exerciseNames;
    }

    // EFFECTS: returns a list of names of the exercises in the exerciseList (all created exercises)
    public List<String> listExerciseNames() {
        return listExerciseNames(exerciseList);
    }

    // MODIFIES: this
    // EFFECTS: returns filtered list of exercises, filtering by given exerciseType
    public List<String> filterByType(String exerciseType) {
        List<Exercise> filteredExerciseList = new ArrayList<>();
        for (Exercise e : exerciseList) {
            if (e.getExerciseType().equals(exerciseType)) {
                filteredExerciseList.add(e);
            }
        }
        return listExerciseNames(filteredExerciseList);
    }

    // EFFECTS: returns exercises in the exercise list as a JSON array
    public JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Exercise e : exerciseList) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }
}
