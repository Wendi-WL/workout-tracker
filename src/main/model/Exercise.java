package model;

import org.json.JSONObject;

import java.util.Objects;

// Represents an exercise with details of its name, type, weight, sets, reps, rest time, and additional notes
public class Exercise {

    private String name;
    private String exerciseType;
    private double weight;
    private int sets;
    private int reps;
    private int restTime;
    private String note;

    // REQUIRES: weight >= 0, sets > 0, reps > 0, restTime > 0
    // EFFECTS: constructs a new instance of Exercise, initialized with parameters
    public Exercise(String name, String exerciseType, double weight, int sets, int reps, int restTime, String note) {
        this.name = name;
        this.exerciseType = exerciseType;
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
        this.restTime = restTime;
        this.note = note;
    }

    //getters
    public String getName() {
        return name;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public double getWeight() {
        return weight;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public int getRestTime() {
        return restTime;
    }

    public String getNote() {
        return note;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // EFFECTS: returns a String of the exercise details
    public String exerciseDetails() {
        return name + " is a(n) " + exerciseType + " exercise.\n"
                + "Weight: " + weight + "lbs\n"
                + "Sets: " + sets + ", Reps: " + reps + ", Rest time: " + restTime + " seconds\n"
                + "Notes: " + note;
    }

    // EFFECTS: returns exercise as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("exercise type", exerciseType);
        json.put("weight", weight);
        json.put("sets", sets);
        json.put("reps", reps);
        json.put("rest time", restTime);
        json.put("note", note);
        return json;
    }

    // EFFECTS: returns true if all fields of Exercise are equivalent between two Exercise objects
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exercise exercise = (Exercise) o;
        return Double.compare(weight, exercise.weight) == 0 && sets == exercise.sets && reps == exercise.reps
                && restTime == exercise.restTime && Objects.equals(name, exercise.name)
                && Objects.equals(exerciseType, exercise.exerciseType) && Objects.equals(note, exercise.note);
    }

    // EFFECTS: overridden hashCode based on overridden equals
    @Override
    public int hashCode() {
        return Objects.hash(name, exerciseType, weight, sets, reps, restTime, note);
    }
}
