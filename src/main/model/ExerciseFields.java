package model;

public enum ExerciseFields {
    NAME("Name"),
    EXERCISE_TYPE("Exercise Type"),
    WEIGHT("Weight (in lbs)"),
    SETS("Sets"),
    REPS("Reps"),
    REST_TIME("Rest Time (in s)"),
    NOTE("Note");

    private final String field;

    ExerciseFields(String field) {
        this.field = field;
    }

    // EFFECTS: returns String representation of field
    public String getString() {
        return field;
    }
}
