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
}
