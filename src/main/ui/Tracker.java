package ui;

import model.*;

import java.util.Scanner;

//TODO: requires/modifies/effects clauses

// Workout Tracker Application
public class Tracker {

    private static final String EXERCISE_COMMAND = "e";
    private static final String WORKOUT_COMMAND = "w";
    private static final String QUIT_COMMAND = "quit";
    private static final String CREATE_NEW_COMMAND = "create";
    private static final String VIEW_COMMAND = "view";
    private static final String BACK_COMMAND = "back";
    private static final String SELECT_COMMAND = "select";
    private static final String EDIT_COMMAND = "edit";
    private static final String DELETE_COMMAND = "delete";
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String FILTER_COMMAND = "filter";
    private static final String SORT_BY_DATE_COMMAND = "sort";
    private static final String NAME_COMMAND = "n";
    private static final String TYPE_COMMAND = "t";
    private static final String WEIGHT_COMMAND = "w";
    private static final String SETS_COMMAND = "s";
    private static final String REPS_COMMAND = "r";
    private static final String REST_TIME_COMMAND = "rt";
    private static final String NOTE_COMMAND = "note";
    private static final String DATE_COMMAND = "d";
    private static final String LOCATION_COMMAND = "l";

    private ExerciseList exerciseList;
    private WorkoutHistory workoutHistory;
    private final Scanner input;

    public Tracker() {
        exerciseList = new ExerciseList();
        workoutHistory = new WorkoutHistory();
        input = new Scanner(System.in);
    }

    // EFFECTS: starts the program by showing welcome message and main menu
    public void runProgram() {
        System.out.println("Welcome to the Workout Tracker!");
        printMainMenu();
    }

    // EFFECTS: prints main menu and options
    private void printMainMenu() {
        System.out.println("MAIN MENU");
        System.out.println("Enter '" + EXERCISE_COMMAND + "' to create, edit, or view exercises.");
        System.out.println("Enter '" + WORKOUT_COMMAND + "' to create, edit, or view workouts.");
        System.out.println("To quit, enter '" + QUIT_COMMAND + "' at any time.");
        handleMainInput();
    }

    private void printExercisesMenu() {
        System.out.println("EXERCISES MENU");
        System.out.println("Enter '" + VIEW_COMMAND + "' to view a list of all created exercises.");
        System.out.println("Enter '" + CREATE_NEW_COMMAND + "' to create a new exercise.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the main menu.");
        handleExercisesInput();
    }

    private void printWorkoutsMenu() {
        System.out.println("WORKOUTS MENU");
        System.out.println("Enter '" + VIEW_COMMAND + "' to view the workout history.");
        System.out.println("Enter '" + CREATE_NEW_COMMAND + "' to create a new workout.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the main menu.");
        handleWorkoutsInput();
    }

    private void printExerciseList() {
        int num = 1;
        System.out.println("EXERCISE LIST");
        for (String s : exerciseList.listExercises()) {
            System.out.println(num + ". " + s);
            num++;
        }
    }

    private void printExerciseListMenu() {
        if (exerciseList.getExerciseList().isEmpty()) {
            System.out.println("No exercises created so far. Create an exercise from the exercises menu.");
            System.out.println("Returning to the exercises menu...");
            printExercisesMenu();
        } else {
            System.out.println("Enter '" + SELECT_COMMAND + "' to view and/or edit a selected exercise.");
            System.out.println("Enter '" + FILTER_COMMAND + "' to view exercises filtered by a specified type.");
            System.out.println("Enter '" + BACK_COMMAND + "' to return to the exercises menu.");
            handleExerciseListInput();
        }
    }

    private void createExercise() {
        Exercise exercise;

        System.out.print("Enter exercise name: ");
        String name = input.next(); //TODO: how to make it take spaces (use nextLine without skipping inputs)
        System.out.print("Enter exercise type: ");
        String exerciseType = input.next();
        System.out.print("Enter weight (in lbs): ");
        double weight = input.nextDouble();
        System.out.print("Enter sets: ");
        int sets = input.nextInt();
        System.out.print("Enter reps: ");
        int reps = input.nextInt();
        System.out.print("Enter rest time (in seconds): ");
        int restTime = input.nextInt();

        exercise = new Exercise(name, exerciseType, weight, sets, reps, restTime);
        exerciseList.create(exercise);
        System.out.println("\nExercise created: ");
        System.out.println(exercise.exerciseDetails() + "\n");

        printExerciseList();
        printExerciseListMenu();
    }

    private void printWorkoutHistory() {
        int num = 1;
        System.out.println("WORKOUT HISTORY");
        for (String s : workoutHistory.listWorkoutDetails()) {
            System.out.println(num + ". " + s);
            num++;
        }
        printWorkoutHistoryMenu();
    }

    private void printWorkoutHistoryMenu() {
        if (workoutHistory.getWorkouts().isEmpty()) {
            System.out.println("No workouts created so far. Create a workout from the workouts menu.");
            System.out.println("Returning to the workouts menu...");
            printWorkoutsMenu();
        } else {
            System.out.println("Enter '" + SELECT_COMMAND + "' to view and/or edit a selected workout.");
            System.out.println("Enter '" + SORT_BY_DATE_COMMAND + "' to view workouts sorted by date (recent on top).");
            System.out.println("Enter '" + FILTER_COMMAND + "' to view workouts filtered by a specified type.");
            System.out.println("Enter '" + BACK_COMMAND + "' to return to the workouts menu.");
            handleWorkoutHistoryInput();
        }
    }

    private void createWorkout() {
        Workout workout;

        System.out.println("Entering workout date");
        System.out.print("Enter year: ");
        int year = input.nextInt();
        System.out.print("Enter month: ");
        int month = input.nextInt();
        System.out.print("Enter day: ");
        int day = input.nextInt();
        System.out.print("Enter workout type: ");
        String workoutType = input.next();
        System.out.print("Enter location: ");
        String location = input.next();

        workout = new Workout(year, month, day, workoutType, location);
        workoutHistory.addWorkout(workout);
        System.out.println("\nWorkout created:");
        System.out.println(workout.workoutDetails() + "\n");

        printWorkoutHistory();
    }

    private void selectExercise() {
        System.out.print("Enter the number of the exercise you would like to select: ");
        int exerciseIndex = input.nextInt() - 1;
        Exercise e = exerciseList.getExerciseList().get(exerciseIndex);
        printSelectedExercise(e);
    }

    private void printSelectedExercise(Exercise e) {
        System.out.println("SELECTED EXERCISE");
        System.out.println(e.exerciseDetails());
        printSelectExerciseMenu(e);
    }

    private void printSelectExerciseMenu(Exercise e) {
        System.out.println("Enter '" + EDIT_COMMAND + "' to edit this exercise.");
        System.out.println("Enter '" + DELETE_COMMAND + "' to delete this exercise from the list of all exercises.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the exercise list.");
        handleSelectExerciseInput(e);
    }

    private void printEditExerciseMenu(Exercise e) {
        System.out.println("Enter '" + NAME_COMMAND + "' to edit the exercise name, '"
                + TYPE_COMMAND + "' to edit the exercise type, '"
                + WEIGHT_COMMAND + "' to edit the weight, '"
                + SETS_COMMAND + "' to edit the sets, '"
                + REPS_COMMAND + "' to edit the reps, '"
                + REST_TIME_COMMAND + "' to edit the rest time, '"
                + NOTE_COMMAND + "' to add a note or edit the note.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the selected exercise.");
        handleEditExerciseInput(e);
    }

    private void editExercise(Exercise e, String field) { //TODO: abstract to make shorter! or suppress warnings
        switch (field) {
            case NAME_COMMAND:
                System.out.println("Current name: " + e.getName());
                System.out.print("Enter new name: ");
                String name = input.nextLine();
                e.setName(name);
                break;
            case TYPE_COMMAND:
                System.out.println("Current exercise type: " + e.getExerciseType());
                System.out.print("Enter new type: ");
                String type = input.nextLine();
                e.setExerciseType(type);
                break;
            case WEIGHT_COMMAND:
                System.out.println("Current weight (lbs): " + e.getWeight());
                System.out.print("Enter new weight (lbs): ");
                double weight = input.nextDouble();
                e.setWeight(weight);
                break;
            case SETS_COMMAND:
                System.out.println("Current sets: " + e.getSets());
                System.out.print("Enter new sets: ");
                int sets = input.nextInt();
                e.setSets(sets);
                break;
            case REPS_COMMAND:
                System.out.println("Current reps: " + e.getReps());
                System.out.print("Enter new reps: ");
                int reps = input.nextInt();
                e.setReps(reps);
                break;
            case REST_TIME_COMMAND:
                System.out.println("Current rest time (seconds): " + e.getRestTime());
                System.out.print("Enter new rest time (seconds): ");
                int restTime = input.nextInt();
                e.setRestTime(restTime);
                break;
            case NOTE_COMMAND:
                if (e.getNote().isEmpty()) {
                    System.out.print("Add a new note: ");
                } else {
                    System.out.println("Current note: " + e.getName());
                    System.out.print("Rewrite note: ");
                }
                String note = input.nextLine();
                e.setNote(note);
                break;
        }
        printSelectedExercise(e);
    }

    private void deleteExercise(Exercise e) {
        exerciseList.delete(e);
        printExerciseList();
        printExerciseListMenu();
    }

    private void filterExercises() {
        System.out.print("Enter the exercise type to filter for: ");
        String exerciseType = input.next();

        System.out.println("EXERCISE LIST filtered for '" + exerciseType + "'.");

        if (exerciseList.filterByType(exerciseType).isEmpty()) {
            System.out.println("No results.");
        } else {
            for (String s : exerciseList.filterByType(exerciseType)) {
                System.out.println("- " + s);
            }
        }
        printExercisesMenu();
    }

    private void selectWorkout() {
        System.out.print("Enter the number of the workout you would like to select: ");
        int workoutIndex = input.nextInt() - 1;
        Workout w = workoutHistory.getWorkouts().get(workoutIndex);
        printSelectedWorkout(w);
    }

    private void printSelectedWorkout(Workout w) {
        System.out.println("SELECTED WORKOUT");
        System.out.println(w.workoutDetails());
        printSelectWorkoutMenu(w);
    }

    private void printSelectWorkoutMenu(Workout w) {
        System.out.println("Enter '" + EDIT_COMMAND + "' to edit the details of this workout.");
        System.out.println("Enter '" + VIEW_COMMAND + "' to view and/or edit the exercises in this workout.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the workout history.");
        handleSelectWorkoutInput(w);
    }

    private void printEditWorkoutMenu(Workout w) {
        System.out.println("Enter '" + DATE_COMMAND + "' to edit the workout date, '"
                + TYPE_COMMAND + "' to edit the workout type, '"
                + LOCATION_COMMAND + "' to edit the location.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the selected workout.");
        handleEditWorkoutInput(w);
    }

    private void editWorkout(Workout w, String field) { //TODO: abstract to make shorter! or suppress warnings
        switch (field) {
            case DATE_COMMAND:
                System.out.println("Current date: "
                        + w.getDate().get(0) + "-" + w.getDate().get(1) + "-" + w.getDate().get(2));
                System.out.println("Enter new year: ");
                int year = input.nextInt();
                System.out.println("Enter new month: ");
                int month = input.nextInt();
                System.out.println("Enter new day: ");
                int day = input.nextInt();
                w.setDate(year, month, day);
                break;
            case TYPE_COMMAND:
                System.out.println("Current workout type: " + w.getWorkoutType());
                System.out.print("Enter new type: ");
                String type = input.nextLine();
                w.setWorkoutType(type);
                break;
            case LOCATION_COMMAND:
                System.out.println("Current location: " + w.getLocation());
                System.out.print("Enter new location: ");
                String location = input.nextLine();
                w.setLocation(location);
                break;
        }
        printSelectedWorkout(w);
    }

    private void printWorkoutExercises(Workout w) {
        if (w.listExercises().isEmpty()) {
            System.out.println(w.workoutDetails());
            System.out.println("This workout has no exercises.");
        } else {
            System.out.println(w.workoutDetailsAndExercises());
        }
        printWorkoutExercisesMenu(w);
    }

    private void printWorkoutExercisesMenu(Workout w) {
        System.out.println("Enter '" + ADD_COMMAND + "' to add an exercise to the workout or '"
                + REMOVE_COMMAND + "' to remove an exercise from the workout.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the selected workout.");
        handleWorkoutExercisesInput(w);
    }

    private void addExerciseToWorkout(Workout w) {
        printExerciseList();
        System.out.print("Enter the number of the exercise you would like to add: ");
        int exerciseIndex = input.nextInt() - 1;
        Exercise e = exerciseList.getExerciseList().get(exerciseIndex);
        w.addExercise(e);
        System.out.println(e.getName() + " has been added to the workout.");
        printWorkoutExercises(w);
    }

    private void removeExerciseFromWorkout(Workout w) {
        if (w.listExercises().isEmpty()) {
            System.out.println("This workout has no exercises. Please add an exercise instead.");
            printWorkoutExercisesMenu(w);
        } else {
            int num = 1;
            for (String s : w.listExercises()) {
                System.out.println(num + ". " + s);
                num++;
            }
            System.out.println("Enter the number of the exercise you would like to remove: ");
            int exerciseIndex = input.nextInt() - 1;
            Exercise e = w.getExercises().get(exerciseIndex);
            w.removeExercise(e);
            System.out.println(e.getName() + " has been removed from the workout.");
            printWorkoutExercises(w);
        }
    }

    private void sortWorkouts() {
        for (String s : workoutHistory.sortByDate()) {
            System.out.println("- " + s);
        }
        printWorkoutsMenu();
    }

    private void filterWorkouts() {
        System.out.print("Enter the workout type to filter for: ");
        String workoutType = input.next();
        System.out.println("WORKOUT HISTORY filtered for '" + workoutType + "'.");
        if (workoutHistory.filterByType(workoutType).isEmpty()) {
            System.out.println("No results.");
        } else {
            for (String s : workoutHistory.filterByType(workoutType)) {
                System.out.println("- " + s);
            }
        }
        printWorkoutsMenu();
    }

    // EFFECTS: takes user input and re-formats String to all lowercase and without whitespace or quotation marks
    private String getUserInputString() {
        String str = input.nextLine();
        str = str.toLowerCase();
        str = str.trim();
        str = str.replaceAll("\"|'", "");
        return str;
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: exits program or returns to the appropriate previous menu
    private void handleInput(String str, String menu) {
        if (!str.isEmpty()) {
            if (str.equals(QUIT_COMMAND)) {
                endProgram();
            } else {
                System.out.println("Invalid command, please try again.");
                switch (menu) {
                    case "Main":
                        printMainMenu();
                        break;
                    case "Exercises":
                        printExercisesMenu();
                        break;
                    case "Workouts":
                        printWorkoutsMenu();
                        break;
                    case "Exercise List":
                        printExerciseListMenu();
                        break;
                    case "Workout History":
                        printWorkoutHistoryMenu();
                        break;
                }
            }
        }
    }

    private void handleInput(String str, String menu, Exercise e) {
        handleInput(str, menu);
        System.out.println("Invalid command, please try again.");
        switch (menu) {
            case "Select Exercise":
                printSelectExerciseMenu(e);
                break;
            case "Edit Exercise":
                printEditExerciseMenu(e);
                break;
        }
    }

    private void handleInput(String str, String menu, Workout w) {
        handleInput(str, menu);
        System.out.println("Invalid command, please try again.");
        switch (menu) {
            case "Select Workout":
                printSelectWorkoutMenu(w);
            case "Edit Workout":
                printEditWorkoutMenu(w);
            case "Workout Exercises":
                printWorkoutExercisesMenu(w);
        }
    }

    private void handleMainInput() {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            switch (str) {
                case EXERCISE_COMMAND:
                    printExercisesMenu();
                    break;
                case WORKOUT_COMMAND:
                    printWorkoutsMenu();
                    break;
                default:
                    handleInput(str, "Main");
            }
        }
    }

    // EFFECTS: prints menu options and info depending on input str
    private void handleExercisesInput() {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            switch (str) {
                case VIEW_COMMAND:
                    printExerciseList();
                    printExerciseListMenu();
                    break;
                case CREATE_NEW_COMMAND:
                    createExercise();
                    break;
                case BACK_COMMAND:
                    printMainMenu();
                    break;
                default:
                    handleInput(str, "Exercises");
            }
        }
    }

    // EFFECTS: prints menu options and info depending on input str
    private void handleWorkoutsInput() {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            switch (str) {
                case VIEW_COMMAND:
                    printWorkoutHistory();
                    break;
                case CREATE_NEW_COMMAND:
                    createWorkout();
                    break;
                case BACK_COMMAND:
                    printMainMenu();
                    break;
                default:
                    handleInput(str, "Workouts");
            }
        }
    }

    private void handleExerciseListInput() {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            switch (str) {
                case SELECT_COMMAND:
                    selectExercise();
                    break;
                case FILTER_COMMAND:
                    filterExercises();
                    break;
                case BACK_COMMAND:
                    printExercisesMenu();
                    break;
                default:
                    handleInput(str, "Exercise List");
            }
        }
    }

    private void handleWorkoutHistoryInput() {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            switch (str) {
                case SELECT_COMMAND:
                    selectWorkout();
                    break;
                case SORT_BY_DATE_COMMAND:
                    sortWorkouts();
                    break;
                case FILTER_COMMAND:
                    filterWorkouts();
                    break;
                case BACK_COMMAND:
                    printWorkoutsMenu();
                    break;
                default:
                    handleInput(str, "Workout History");
            }
        }
    }

    private void handleSelectExerciseInput(Exercise e) {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            switch (str) {
                case EDIT_COMMAND:
                    printEditExerciseMenu(e);
                    break;
                case DELETE_COMMAND:
                    deleteExercise(e);
                    break;
                case BACK_COMMAND:
                    printExerciseList();
                    printExerciseListMenu();
                    break;
                default:
                    handleInput(str, "Select Exercise", e);
            }
        }
    }

    private void handleSelectWorkoutInput(Workout w) {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            switch (str) {
                case EDIT_COMMAND:
                    printEditWorkoutMenu(w);
                    break;
                case VIEW_COMMAND:
                    printWorkoutExercises(w);
                    break;
                case BACK_COMMAND:
                    printWorkoutHistory();
                    break;
                default:
                    handleInput(str, "Select Workout", w);
            }
        }
    }

    private void handleEditExerciseInput(Exercise e) {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            if (str.equals(BACK_COMMAND)) {
                printSelectExerciseMenu(e);
            } else if (str.equals(NAME_COMMAND) || str.equals(TYPE_COMMAND) || str.equals(WEIGHT_COMMAND)
                    || str.equals(SETS_COMMAND) || str.equals(REPS_COMMAND) || str.equals(REST_TIME_COMMAND)
                    || str.equals(NOTE_COMMAND)) {
                editExercise(e, str);
            } else {
                handleInput(str, "Edit Exercise", e);
            }
        }
    }

    private void handleEditWorkoutInput(Workout w) {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            if (str.equals(BACK_COMMAND)) {
                printSelectWorkoutMenu(w);
            } else if (str.equals(DATE_COMMAND) || str.equals(TYPE_COMMAND) || str.equals(LOCATION_COMMAND)) {
                editWorkout(w, str);
            } else {
                handleInput(str, "Edit Workout", w);
            }
        }
    }

    private void handleWorkoutExercisesInput(Workout w) {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            switch (str) {
                case ADD_COMMAND:
                    addExerciseToWorkout(w);
                    break;
                case REMOVE_COMMAND:
                    removeExerciseFromWorkout(w);
                    break;
                case BACK_COMMAND:
                    printEditWorkoutMenu(w);
                    break;
                default:
                    handleInput(str, "Workout Exercises", w);
            }
        }
    }

    // EFFECTS: stops receiving user input
    public void endProgram() {
        System.out.println("Quitting application... Thanks for using Workout Tracker!");
        input.close();
    }

}
