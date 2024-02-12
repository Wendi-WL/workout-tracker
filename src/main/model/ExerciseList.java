package model;

import java.util.ArrayList;
import java.util.List;

// Represents a list that stores all created exercises
public class ExerciseList {

    private List<Exercise> exerciseList;

    // EFFECTS: constructs a new instance of ExerciseList, initialized as an empty list of exercises
    public ExerciseList() {
        exerciseList = new ArrayList<>();
    }

    //getter
    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    // MODIFIES: this
    // EFFECTS: adds the exercise to the list of all created exercises
    public void create(Exercise e) {
        exerciseList.add(e);
    }

    // REQUIRES: e is in exerciseList
    // MODIFIES: this
    // EFFECTS: removes the exercise from the list of all created exercises
    public void delete(Exercise e) {
        exerciseList.remove(e);
    }

    // REQUIRES: e is in exerciseList
    // EFFECTS: returns a String of the details of the selected exercise
    public String exerciseDetails(Exercise e) {
        return e.exerciseDetails();
    }

    // EFFECTS: returns a list of names of the exercises in le
    public List<String> listExercises(List<Exercise> le) {
        List<String> exerciseNames = new ArrayList<>();
        for (Exercise e : le) {
            exerciseNames.add(e.getName());
        }
        return exerciseNames;
    }

    // EFFECTS: returns a list of names of the exercises in the exerciseList (all created exercises)
    public List<String> listExercises() {
        return listExercises(exerciseList);
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
        return listExercises(filteredExerciseList);
    }

}
