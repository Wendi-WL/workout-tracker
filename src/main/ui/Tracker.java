package ui;

import model.*;

import java.util.Scanner;

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

    // EFFECTS: constructs a new Tracker with an ExerciseList, a WorkoutHistory, and a Scanner for user input
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

    // EFFECTS: prints main menu with options, and prompts user input
    private void printMainMenu() {
        System.out.println("MAIN MENU");
        System.out.println("Enter '" + EXERCISE_COMMAND + "' to create, edit, or view exercises.");
        System.out.println("Enter '" + WORKOUT_COMMAND + "' to create, edit, or view workouts.");
        System.out.println("To quit, enter '" + QUIT_COMMAND + "' at any time.");
        handleMainInput();
    }

    // EFFECTS: prints exercises menu with options, and prompts user input
    private void printExercisesMenu() {
        System.out.println("EXERCISES MENU");
        System.out.println("Enter '" + VIEW_COMMAND + "' to view a list of all created exercises.");
        System.out.println("Enter '" + CREATE_NEW_COMMAND + "' to create a new exercise.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the main menu.");
        handleExercisesInput();
    }

    // EFFECTS: prints workouts menu with options, and prompts user input
    private void printWorkoutsMenu() {
        System.out.println("WORKOUTS MENU");
        System.out.println("Enter '" + VIEW_COMMAND + "' to view the workout history.");
        System.out.println("Enter '" + CREATE_NEW_COMMAND + "' to create a new workout.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the main menu.");
        handleWorkoutsInput();
    }

    // EFFECTS: prints exercise list
    private void printExerciseList() {
        int num = 1;
        System.out.println("EXERCISE LIST");
        for (String s : exerciseList.listExercises()) {
            System.out.println(num + ". " + s);
            num++;
        }
    }

    // EFFECTS: prints error message and returns to exercises menu if no exercises created,
    // otherwise prints exercise list menu with options, and prompts user input
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

    // MODIFIES: this
    // EFFECTS: creates a new exercise based on prompted user inputs, and prints exercise details,
    // then prints the exercise list and redirects to exercise list menu, and prompts user input
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

    // EFFECTS: prints workout history, then redirects to workout history menu
    private void printWorkoutHistory() {
        int num = 1;
        System.out.println("WORKOUT HISTORY");
        for (String s : workoutHistory.listWorkoutDetails()) {
            System.out.println(num + ". " + s);
            num++;
        }
        printWorkoutHistoryMenu();
    }

    // EFFECTS: prints error message and returns to workouts menu if no workouts created,
    // otherwise prints workout history menu with options, and prompts user input
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

    // MODIFIES: this
    // EFFECTS: creates a new workout based on prompted user inputs, and prints workout details,
    // then redirects to workouts history
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

    // EFFECTS: selects and prints an exercise based on user's input of exercise number
    private void selectExercise() {
        System.out.print("Enter the number of the exercise you would like to select: ");
        int exerciseIndex = input.nextInt() - 1;
        Exercise e = exerciseList.getExerciseList().get(exerciseIndex);
        printSelectedExercise(e);
    }

    // EFFECTS: prints the selected exercise's details
    private void printSelectedExercise(Exercise e) {
        System.out.println("SELECTED EXERCISE");
        System.out.println(e.exerciseDetails());
        printSelectExerciseMenu(e);
    }

    // EFFECTS: prints the select exercise menu options, and prompts user input
    private void printSelectExerciseMenu(Exercise e) {
        System.out.println("Enter '" + EDIT_COMMAND + "' to edit this exercise.");
        System.out.println("Enter '" + DELETE_COMMAND + "' to delete this exercise from the list of all exercises.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the exercise list.");
        handleSelectExerciseInput(e);
    }

    // EFFECTS: prints the edit exercise menu options, and prompts user input
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

    // EFFECTS: returns String of calling appropriate getter for the field
    private String editExerciseGetter(Exercise e, String field) {
        switch (field) {
            case NAME_COMMAND:
                return e.getName();
            case TYPE_COMMAND:
                return e.getExerciseType();
            case WEIGHT_COMMAND:
                return String.valueOf(e.getWeight());
            case SETS_COMMAND:
                return String.valueOf(e.getSets());
            case REPS_COMMAND:
                return String.valueOf(e.getReps());
            case REST_TIME_COMMAND:
                return String.valueOf(e.getRestTime());
            case NOTE_COMMAND:
                return e.getNote();
            default:
                return null;
        }
    }

    // EFFECTS: returns name of specified field
    private String editExerciseFieldName(String field) {
        switch (field) {
            case NAME_COMMAND:
                return "name";
            case TYPE_COMMAND:
                return "exercise type";
            case WEIGHT_COMMAND:
                return "weight (lbs)";
            case SETS_COMMAND:
                return "sets";
            case REPS_COMMAND:
                return "reps";
            case REST_TIME_COMMAND:
                return "rest time (seconds)";
            case NOTE_COMMAND:
                return "note";
            default:
                return null;
        }
    }

    // EFFECTS: prints out current details of specified exercise field, then prompts new value
    private void editExercisePrompts(Exercise e, String field) {
        String fieldName = editExerciseFieldName(field);
        System.out.println("Current " + fieldName + ": " + editExerciseGetter(e, field));
        System.out.print("Enter new " + fieldName + ": ");
    }

    // MODIFIES: this
    // EFFECTS: edits then prints updated exercise details
    private void editExercise(Exercise e, String field) { //TODO: abstract to make shorter! or suppress warnings
        editExercisePrompts(e, field);
        if (field.equals(NAME_COMMAND) || field.equals(TYPE_COMMAND) || field.equals(WEIGHT_COMMAND)) {
            editExerciseSub1(e, field);
        } else {
            editExerciseSub2(e, field);
        }
        printSelectedExercise(e);
    }

    // EFFECTS: edits the selected exercise's specified field (out of first grouping of fields),
    // and updates to given new value,
    private void editExerciseSub1(Exercise e, String field) {
        switch (field) {
            case NAME_COMMAND:
                String name = input.next();
                e.setName(name);
                break;
            case TYPE_COMMAND:
                String type = input.next();
                e.setExerciseType(type);
                break;
            case WEIGHT_COMMAND:
                double weight = input.nextDouble();
                e.setWeight(weight);
                break;
        }
    }

    // EFFECTS: edits the selected exercise's specified field (out of first grouping of fields),
    // and updates to given new value,
    private void editExerciseSub2(Exercise e, String field) {
        switch (field) {
            case SETS_COMMAND:
                int sets = input.nextInt();
                e.setSets(sets);
                break;
            case REPS_COMMAND:
                int reps = input.nextInt();
                e.setReps(reps);
                break;
            case REST_TIME_COMMAND:
                int restTime = input.nextInt();
                e.setRestTime(restTime);
                break;
            case NOTE_COMMAND:
                String note = input.next();
                e.setNote(note);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes selected exercise from list of exercises, then redirects to exercise list
    private void deleteExercise(Exercise e) {
        exerciseList.delete(e);
        printExerciseList();
        printExerciseListMenu();
    }

    // EFFECTS: prints filtered list of exercises, filtering by type specified by user input,
    // then returns to exercises menu
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

    // EFFECTS: selects and prints a workout based on user's input of workout number
    private void selectWorkout() {
        System.out.print("Enter the number of the workout you would like to select: ");
        int workoutIndex = input.nextInt() - 1;
        Workout w = workoutHistory.getWorkouts().get(workoutIndex);
        printSelectedWorkout(w);
    }

    // EFFECTS: prints the selected workout's details
    private void printSelectedWorkout(Workout w) {
        System.out.println("SELECTED WORKOUT");
        System.out.println(w.workoutDetails());
        printSelectWorkoutMenu(w);
    }

    // EFFECTS: prints the select workout menu options, and prompts user input
    private void printSelectWorkoutMenu(Workout w) {
        System.out.println("Enter '" + EDIT_COMMAND + "' to edit the details of this workout.");
        System.out.println("Enter '" + VIEW_COMMAND + "' to view and/or edit the exercises in this workout.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the workout history.");
        handleSelectWorkoutInput(w);
    }

    // EFFECTS: prints the edit workout menu options, and prompts user input
    private void printEditWorkoutMenu(Workout w) {
        System.out.println("Enter '" + DATE_COMMAND + "' to edit the workout date, '"
                + TYPE_COMMAND + "' to edit the workout type, '"
                + LOCATION_COMMAND + "' to edit the location.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the selected workout.");
        handleEditWorkoutInput(w);
    }

    // EFFECTS: returns String of calling appropriate getter for the field
    private String editWorkoutGetter(Workout w, String field) {
        switch (field) {
            case DATE_COMMAND:
                return w.formattedDate();
            case TYPE_COMMAND:
                return w.getWorkoutType();
            case LOCATION_COMMAND:
                return w.getLocation();
            default:
                return null;
        }
    }

    // EFFECTS: returns name of specified field
    private String editWorkoutFieldName(String field) {
        switch (field) {
            case DATE_COMMAND:
                return "date";
            case TYPE_COMMAND:
                return "workout type";
            case LOCATION_COMMAND:
                return "location";
            default:
                return null;
        }
    }

    // EFFECTS: prints out current details of specified exercise field, then prompts new value
    private void editWorkoutPrompts(Workout w, String field) {
        String fieldName = editWorkoutFieldName(field);
        System.out.println("Current " + fieldName + ": " + editWorkoutGetter(w, field));
        System.out.print("Enter new " + fieldName  + ": ");
    }

    // MODIFIES: this
    // EFFECTS: edits the selected exercise's specified field and updates to given new value,
    // then prints updated exercise details
    private void editWorkout(Workout w, String field) {
        editWorkoutPrompts(w, field);
        switch (field) {
            case DATE_COMMAND:
                System.out.print("(enter in format YYYY MM DD) ");
                int year = input.nextInt();
                int month = input.nextInt();
                int day = input.nextInt();
                w.setDate(year, month, day);
                break;
            case TYPE_COMMAND:
                String type = input.next();
                w.setWorkoutType(type);
                break;
            case LOCATION_COMMAND:
                String location = input.next();
                w.setLocation(location);
                break;
        }
        printSelectedWorkout(w);
    }

    // EFFECTS: prints workout details and the names of the exercises in the workout,
    // then redirects to workout exercises menu
    private void printWorkoutExercises(Workout w) {
        if (w.listExercises().isEmpty()) {
            System.out.println(w.workoutDetails());
            System.out.println("This workout has no exercises.");
        } else {
            System.out.println(w.workoutDetailsAndExercises());
        }
        printWorkoutExercisesMenu(w);
    }

    // EFFECTS: prints workout exercises menu with options, and prompts user input
    private void printWorkoutExercisesMenu(Workout w) {
        System.out.println("Enter '" + ADD_COMMAND + "' to add an exercise to the workout or '"
                + REMOVE_COMMAND + "' to remove an exercise from the workout.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the selected workout.");
        handleWorkoutExercisesInput(w);
    }

    // EFFECTS: adds exercise to workout and prints updated workout details
    private void addExerciseToWorkout(Workout w) {
        printExerciseList();
        System.out.print("Enter the number of the exercise you would like to add: ");
        int exerciseIndex = input.nextInt() - 1;
        Exercise e = exerciseList.getExerciseList().get(exerciseIndex);
        w.addExercise(e);
        System.out.println(e.getName() + " has been added to the workout.");
        printWorkoutExercises(w);
    }

    // EFFECTS: removes exercise from workout and prints updated workout details
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

    // EFFECTS: prints sorted list of exercises, sorting by latest date first,
    // then returns to workouts menu
    private void sortWorkouts() {
        for (String s : workoutHistory.sortByDate()) {
            System.out.println("- " + s);
        }
        printWorkoutsMenu();
    }

    // EFFECTS: prints filtered list of exercises, filtering by type specified by user input,
    // then returns to workouts meu
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
        String str = input.next();
        str = str.toLowerCase();
        str = str.trim();
        str = str.replaceAll("\"|'", "");
        return str;
    }

    // EFFECTS: returns true if str is the command to quit the program
    private boolean quitInput(String str) {
        return str.equals(QUIT_COMMAND);
    }

    // EFFECTS: exits program or returns to the appropriate previous menu
    private void handleInput(String str, String menu) {
        if (quitInput(str)) {
            endProgram();
        } else {
            System.out.println("Invalid command, please try again.");
            switch (menu) {
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
                default:
                    printMainMenu();
            }
        }
    }

    // EFFECTS: exits program or returns to the appropriate previous menu with the selected exercise
    private void handleInput(String str, String menu, Exercise e) {
        if (quitInput(str)) {
            endProgram();
        } else {
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
    }

    // EFFECTS: exits program or returns to the appropriate previous menu with the selected workouts
    private void handleInput(String str, String menu, Workout w) {
        if (quitInput(str)) {
            endProgram();
        } else {
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
    }

    // EFFECTS: handles input based on options from the main menu, redirects to appropriate menu or functionality
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

    // EFFECTS: handles input from the exercises menu, redirects to appropriate menu or functionality
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

    // EFFECTS: handles input from the workouts menu, redirects to appropriate menu or functionality
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

    // EFFECTS: handles input from the exercise list menu, redirects to appropriate menu or functionality
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

    // EFFECTS: handles input from the workout history menu, redirects to appropriate menu or functionality
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

    // EFFECTS: handles input from the select exercise menu, redirects to appropriate menu or functionality
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

    // EFFECTS: handles input from the select workout menu, redirects to appropriate menu or functionality
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

    // EFFECTS: handles input from the edit exercise menu, redirects to appropriate menu or functionality
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

    // EFFECTS: handles input from the edit workout menu, redirects to appropriate menu or functionality
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

    // EFFECTS: handles input from the workout exercises menu, redirects to appropriate menu or functionality
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

    // EFFECTS: stops receiving user input and prints closing message
    public void endProgram() {
        input.close();
        System.out.println("Quitting application... Thanks for using Workout Tracker!");
    }

}
