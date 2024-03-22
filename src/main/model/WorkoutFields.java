package model;

public enum WorkoutFields {
    DATE("Date"),
    WORKOUT_TYPE("Workout Type"),
    LOCATION("Location"),
    EXERCISES("Exercises");

    private final String field;

    WorkoutFields(String field) {
        this.field = field;
    }

    // EFFECTS: returns String representation of field
    public String getString() {
        return field;
    }
}
