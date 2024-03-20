package ui;

import model.*;
import persistence.DataReader;
import persistence.DataWriter;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Workout Tracker Application
public class Tracker {

    private static final String LOAD_COMMAND = "load";
    private static final String NO_COMMAND = "no";
    private static final String QUIT_COMMAND = "q";
    private static final String SAVE_COMMAND = "save";
    private static final String EXERCISE_COMMAND = "e";
    private static final String WORKOUT_COMMAND = "w";
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
    private final DataReader reader = new DataReader("./data/exercisesAndWorkouts.json");
    private final DataWriter writer = new DataWriter("./data/exercisesAndWorkouts.json");

    // EFFECTS: constructs a new Tracker with an ExerciseList, a WorkoutHistory, and a Scanner for user input
    public Tracker() {
        exerciseList = new ExerciseList();
        workoutHistory = new WorkoutHistory();
        input = new Scanner(System.in);
    }

    // EFFECTS: starts the program by printing a welcome message then printing load option
    public void runProgram() {
        System.out.println("Welcome to the Workout Tracker!");
        printLoadOption();
    }

    // EFFECTS: prints option to load data from previous save file
    private void printLoadOption() {
        System.out.println("Enter '" + LOAD_COMMAND + "' to load data from previous save file.");
        System.out.println("Enter '" + NO_COMMAND + "' to skip loading data and create new exercises and workouts.");
        handleLoadInput();
    }

    // EFFECTS: prints main menu with options, and prompts user input
    private void printMainMenu() {
        System.out.println("MAIN MENU");
        System.out.println("Enter '" + EXERCISE_COMMAND + "' to create, edit, or view exercises.");
        System.out.println("Enter '" + WORKOUT_COMMAND + "' to create, edit, or view workouts.");
        System.out.println("To quit, enter '" + QUIT_COMMAND + "' at any time.");
        handleMainInput();
    }

    // EFFECTS: prints exercise or workout menu with options, and prompts user input
    private void printExerciseOrWorkoutMenu(String menu) {
        String toView;
        if (menu.equals("exercise")) {
            toView = "a list of all created exercises.";
        } else {
            toView = "the workout history.";
        }

        System.out.println(menu.toUpperCase() + " MENU");
        System.out.println("Enter '" + VIEW_COMMAND + "' to view " + toView);
        System.out.println("Enter '" + CREATE_NEW_COMMAND + "' to create a new " + menu + ".");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the main menu.");

        handleExerciseOrWorkoutInput(menu);
    }

    // EFFECTS: prints exercise list or prints workout history and redirects to the workout history menu
    private void printExerciseListOrWorkoutHistory(String listName) {
        List<String> listToIterate;
        String menu;
        if (listName.equals("exercise list")) {
            listToIterate = exerciseList.listExerciseNames();
            menu = "exercise";
        } else {
            listToIterate = workoutHistory.listWorkoutDetails();
            menu = "workout";
        }

        System.out.println(listName.toUpperCase());
        int num = 1;
        for (String s : listToIterate) {
            System.out.println(num + ". " + s);
            num++;
        }

        if (listToIterate.isEmpty()) {
            System.out.println("No " + menu + "s created so far. Create one from the " + menu + " menu.");
            System.out.println("Redirecting to the " + menu + " menu...");
            printExerciseOrWorkoutMenu(menu);
        } else if (listName.equals("workout history")) {
            printExerciseListOrWorkoutHistoryMenu("workout");
        }
    }

    // EFFECTS: prints error message and returns to previous menu if no exercises/workouts created,
    // otherwise prints exercise list/workout history menu with options, and prompts user input
    private void printExerciseListOrWorkoutHistoryMenu(String menu) {
        System.out.println("Enter '" + SELECT_COMMAND + "' to view and/or edit a selected " + menu + ".");
        System.out.println("Enter '" + FILTER_COMMAND + "' to view " + menu + "s filtered by a specified type.");
        if (menu.equals("workout")) {
            System.out.println("Enter '" + SORT_BY_DATE_COMMAND + "' to view workouts sorted by date (latest first).");
        }
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the " + menu + " menu.");
        handleExerciseListOrWorkoutHistoryInput(menu);
    }

    // MODIFIES: this
    // EFFECTS: creates a new exercise/workout and prints its details,
    // then prints the exercise list/workout history,
    // then redirects to the appropriate menu, and prompts user input
    private void create(String creationType) {
        String details;
        if (creationType.equals("exercise")) {
            Exercise exercise = createExercise();
            details = exercise.exerciseDetails();
        } else {
            Workout workout = createWorkout();
            details = workout.workoutDetails();
        }

        System.out.println("\nNew " + creationType + " created: ");
        System.out.println(details + "\n");

        if (creationType.equals("exercise")) {
            printExerciseListOrWorkoutHistory("exercise list");
            printExerciseListOrWorkoutHistoryMenu("exercise");
        } else {
            printExerciseListOrWorkoutHistory("workout history");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new exercise based on prompted user inputs
    private Exercise createExercise() {
        input.nextLine();
        System.out.print("Enter exercise name: ");
        String name = input.nextLine();
        System.out.print("Enter exercise type: ");
        String exerciseType = input.nextLine();
        System.out.print("Enter weight (in lbs): ");
        double weight = input.nextDouble();
        input.nextLine();
        System.out.print("Enter sets: ");
        int sets = input.nextInt();
        input.nextLine();
        System.out.print("Enter reps: ");
        int reps = input.nextInt();
        input.nextLine();
        System.out.print("Enter rest time (in seconds): ");
        int restTime = input.nextInt();
        input.nextLine();

        Exercise exercise = new Exercise(name, exerciseType, weight, sets, reps, restTime);
        exerciseList.create(exercise);
        return exercise;
    }

    // MODIFIES: this
    // EFFECTS: creates a new workout based on prompted user inputs
    private Workout createWorkout() {
        input.nextLine();
        System.out.println("Enter workout date: (format YYYY MM DD separated by spaces) ");
        int year = input.nextInt();
        int month = input.nextInt();
        int day = input.nextInt();
        input.nextLine();
        System.out.print("Enter workout type: ");
        String workoutType = input.nextLine();
        System.out.print("Enter location: ");
        String location = input.nextLine();

        Workout workout = new Workout(year, month, day, workoutType, location);
        workoutHistory.addWorkout(workout);
        return workout;
    }

    // EFFECTS: selects and prints an exercise/a workout based on user's input of exercise number
    private void select(String selectionType) {
        System.out.print("Enter the number of the " + selectionType + " you would like to select: ");
        int index = input.nextInt() - 1;
        if (selectionType.equals("exercise")) {
            Exercise x = exerciseList.getExerciseList().get(index);
            printSelected(x);
        } else {
            Workout x = workoutHistory.getWorkouts().get(index);
            printSelected(x);
        }
    }

    // EFFECTS: prints the selected exercise's details
    private void printSelected(Exercise e) {
        System.out.println("SELECTED EXERCISE");
        System.out.println(e.exerciseDetails());
        printSelectMenu(e);
    }

    // EFFECTS: prints the selected workout's details
    private void printSelected(Workout w) {
        System.out.println("SELECTED WORKOUT");
        System.out.println(w.workoutDetails());
        printSelectMenu(w);
    }

    // EFFECTS: prints the select exercise menu options, and prompts user input
    private void printSelectMenu(Exercise e) {
        System.out.println("Enter '" + EDIT_COMMAND + "' to edit this exercise.");
        System.out.println("Enter '" + DELETE_COMMAND + "' to delete this exercise from the list of all exercises.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the exercise list.");
        handleSelectInput(e);
    }

    // EFFECTS: prints the select workout menu options, and prompts user input
    private void printSelectMenu(Workout w) {
        System.out.println("Enter '" + EDIT_COMMAND + "' to edit the details of this workout.");
        System.out.println("Enter '" + VIEW_COMMAND + "' to view and/or edit the exercises in this workout.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the workout history.");
        handleSelectInput(w);
    }

    // EFFECTS: prints the edit exercise menu options, and prompts user input
    private void printEditMenu(Exercise e) {
        System.out.println("Enter '" + NAME_COMMAND + "' to edit the exercise name, '"
                + TYPE_COMMAND + "' to edit the exercise type, '"
                + WEIGHT_COMMAND + "' to edit the weight, '"
                + SETS_COMMAND + "' to edit the sets, '"
                + REPS_COMMAND + "' to edit the reps, '"
                + REST_TIME_COMMAND + "' to edit the rest time, '"
                + NOTE_COMMAND + "' to add a note or edit the note.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the selected exercise.");
        handleEditInput(e);
    }

    // EFFECTS: prints the edit workout menu options, and prompts user input
    private void printEditMenu(Workout w) {
        System.out.println("Enter '" + DATE_COMMAND + "' to edit the workout date, '"
                + TYPE_COMMAND + "' to edit the workout type, '"
                + LOCATION_COMMAND + "' to edit the location.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the selected workout.");
        handleEditInput(w);
    }

    // MODIFIES: this
    // EFFECTS: edits then prints updated exercise details
    private void edit(Exercise e, String field) {
        editPrompts(e, field);
        if (field.equals(NAME_COMMAND) || field.equals(TYPE_COMMAND) || field.equals(NOTE_COMMAND)) {
            input.nextLine();
        }
        editSetter(e, field);
        if (field.equals(WEIGHT_COMMAND)
                || field.equals(SETS_COMMAND) || field.equals(REPS_COMMAND) || field.equals(REST_TIME_COMMAND)) {
            input.nextLine();
        }
        printSelected(e);
    }

    // MODIFIES: this
    // EFFECTS: edits the selected exercise's specified field and updates to given new value,
    // then prints updated exercise details
    private void edit(Workout w, String field) {
        editPrompts(w, field);
        if (field.equals(TYPE_COMMAND) || field.equals(LOCATION_COMMAND)) {
            input.nextLine();
        }
        editSetter(w, field);
        if (field.equals(DATE_COMMAND)) {
            input.nextLine();
        }
        printSelected(w);
    }

    // EFFECTS: returns String of calling appropriate getter for the field
    private String editGetter(Exercise e, String field) {
        switch (field) {
            case NAME_COMMAND: return e.getName();
            case TYPE_COMMAND: return e.getExerciseType();
            case WEIGHT_COMMAND: return String.valueOf(e.getWeight());
            case SETS_COMMAND: return String.valueOf(e.getSets());
            case REPS_COMMAND: return String.valueOf(e.getReps());
            case REST_TIME_COMMAND: return String.valueOf(e.getRestTime());
            case NOTE_COMMAND: return e.getNote();
            default: return null;
        }
    }

    // EFFECTS: returns String of calling appropriate getter for the field
    private String editGetter(Workout w, String field) {
        switch (field) {
            case DATE_COMMAND: return w.formattedDate();
            case TYPE_COMMAND: return w.getWorkoutType();
            case LOCATION_COMMAND: return w.getLocation();
            default: return null;
        }
    }

    // EFFECTS: returns name of specified field
    private String editFieldName(String selectionType, String field) {
        if (selectionType.equals("exercise")) {
            switch (field) {
                case NAME_COMMAND: return "name";
                case TYPE_COMMAND: return "exercise type";
                case WEIGHT_COMMAND: return "weight (lbs)";
                case SETS_COMMAND: return "sets";
                case REPS_COMMAND: return "reps";
                case REST_TIME_COMMAND: return "rest time (seconds)";
                case NOTE_COMMAND: return "note";
                default: return null;
            }
        } else {
            switch (field) {
                case DATE_COMMAND: return "date";
                case TYPE_COMMAND: return "workout type";
                case LOCATION_COMMAND: return "location";
                default: return null;
            }
        }
    }

    // EFFECTS: prints out current details of specified exercise field, then prompts new value
    private void editPrompts(Exercise e, String field) {
        String fieldName = editFieldName("exercise", field);
        System.out.println("Current " + fieldName + ": " + editGetter(e, field));
        System.out.print("Enter new " + fieldName + ": ");
    }

    // EFFECTS: prints out current details of specified exercise field, then prompts new value
    private void editPrompts(Workout w, String field) {
        String fieldName = editFieldName("workout", field);
        System.out.println("Current " + fieldName + ": " + editGetter(w, field));
        System.out.print("Enter new " + fieldName  + ": ");
    }

    // EFFECTS: edits the selected exercise's specified field and updates to given new value
    private void editSetter(Exercise e, String field) {
        switch (field) {
            case NAME_COMMAND:
                e.setName(input.nextLine());
                break;
            case TYPE_COMMAND:
                e.setExerciseType(input.nextLine());
                break;
            case WEIGHT_COMMAND:
                e.setWeight(input.nextDouble());
                break;
            case SETS_COMMAND:
                e.setSets(input.nextInt());
                break;
            case REPS_COMMAND:
                e.setReps(input.nextInt());
                break;
            case REST_TIME_COMMAND:
                e.setRestTime(input.nextInt());
                break;
            case NOTE_COMMAND:
                e.setNote(input.nextLine());
                break;
        }
    }

    // EFFECTS: edits the selected workout's specified field and updates to given new value
    private void editSetter(Workout w, String field) {
        switch (field) {
            case DATE_COMMAND:
                System.out.print("(format YYYY MM DD separated by spaces) ");
                w.setDate(input.nextInt(), input.nextInt(), input.nextInt());
                break;
            case TYPE_COMMAND:
                w.setWorkoutType(input.nextLine());
                break;
            case LOCATION_COMMAND:
                w.setLocation(input.nextLine());
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes selected exercise from list of exercises, then redirects to the exercise list
    private void deleteExercise(Exercise e) {
        exerciseList.delete(e);
        printExerciseListOrWorkoutHistory("exercise list");
        if (!exerciseList.getExerciseList().isEmpty()) {
            printExerciseListOrWorkoutHistoryMenu("exercise");
        }
    }

    // EFFECTS: prints filtered list of exercises/workouts, filtering by type specified by user input,
    // then returns to the exercise/workout menu
    private void filter(String viewType) {
        System.out.print("Enter the " + viewType + " type to filter for: ");
        String type = input.next();

        List<String> listToFilter;
        if (viewType.equals("exercise")) {
            System.out.print("EXERCISE LIST");
            listToFilter = exerciseList.filterByType(type);
        } else {
            System.out.print("WORKOUT HISTORY");
            listToFilter = workoutHistory.filterByType(type);
        }
        System.out.println(" filtered for '" + type + "'.");

        boolean isEmpty = listToFilter.isEmpty();
        if (isEmpty) {
            System.out.println("No results.");
        } else {
            for (String s : listToFilter) {
                System.out.println("- " + s);
            }
        }
        printExerciseOrWorkoutMenu(viewType);
    }

    // EFFECTS: prints sorted list of exercises, sorting by latest date first,
    // then returns to the workout menu
    private void sortWorkouts() {
        for (String s : workoutHistory.sortByDate()) {
            System.out.println("- " + s);
        }
        printExerciseOrWorkoutMenu("workout");
    }

    // EFFECTS: prints workout details and the names of the exercises in the workout,
    // then redirects to the workout exercise menu
    private void printWorkoutExercises(Workout w) {
        if (w.getExercises().listExerciseNames().isEmpty()) {
            System.out.println(w.workoutDetails());
            System.out.println("This workout has no exercises.");
        } else {
            System.out.println(w.workoutDetailsAndExercises());
        }
        printWorkoutExercisesMenu(w);
    }

    // EFFECTS: prints workout exercise menu with options, and prompts user input
    private void printWorkoutExercisesMenu(Workout w) {
        System.out.println("Enter '" + ADD_COMMAND + "' to add an exercise to the workout or '"
                + REMOVE_COMMAND + "' to remove an exercise from the workout.");
        System.out.println("Enter '" + BACK_COMMAND + "' to return to the selected workout.");
        handleWorkoutExercisesInput(w);
    }

    // EFFECTS: adds exercise to the workout and prints updated workout details
    private void addExerciseToWorkout(Workout w) {
        printExerciseListOrWorkoutHistory("exercise list");
        if (!exerciseList.getExerciseList().isEmpty()) {
            System.out.print("Enter the number of the exercise you would like to add: ");
            int exerciseIndex = input.nextInt() - 1;
            Exercise e = exerciseList.getExerciseList().get(exerciseIndex);
            w.addExercise(e);
            System.out.println(e.getName() + " has been added to the workout.");
            printWorkoutExercises(w);
        }
    }

    // EFFECTS: removes exercise from workout and prints updated workout details
    private void removeExerciseFromWorkout(Workout w) {
        if (w.getExercises().listExerciseNames().isEmpty()) {
            System.out.println("This workout has no exercises. Please add an exercise instead.");
            printWorkoutExercisesMenu(w);
        } else {
            int num = 1;
            for (String s : w.getExercises().listExerciseNames()) {
                System.out.println(num + ". " + s);
                num++;
            }
            System.out.println("Enter the number of the exercise you would like to remove: ");
            int exerciseIndex = input.nextInt() - 1;
            Exercise e = w.getExercises().getExerciseList().get(exerciseIndex);
            w.removeExercise(e);
            System.out.println(e.getName() + " has been removed from the workout.");
            printWorkoutExercises(w);
        }
    }

    // EFFECTS: prints option to save data to file before fully quitting application
    private void printSaveOption() {
        System.out.println("Before you leave, would you like to save data from this session to file?");
        System.out.println("Enter '" + SAVE_COMMAND + "' to save data to file.");
        System.out.println("Enter '" + NO_COMMAND + "' to skip saving data.");
        handleSaveInput();
    }

    // EFFECTS: re-formats user input String to all lowercase and without leading/trailing spaces or quotation marks
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
            printSaveOption();
        } else {
            System.out.println("Invalid command, please try again.");
            switch (menu) {
                case "exercise":
                    printExerciseOrWorkoutMenu("exercise");
                    break;
                case "workout":
                    printExerciseOrWorkoutMenu("workout");
                    break;
                case "exercise list":
                    printExerciseListOrWorkoutHistoryMenu("exercise");
                    break;
                case "workout history":
                    printExerciseListOrWorkoutHistoryMenu("workout");
                    break;
                default:
                    printMainMenu();
            }
        }
    }

    // EFFECTS: exits program or returns to the appropriate previous menu with the selected exercise
    private void handleInput(String str, String menu, Exercise e) {
        if (quitInput(str)) {
            printSaveOption();
        } else {
            System.out.println("Invalid command, please try again.");
            switch (menu) {
                case "select":
                    printSelectMenu(e);
                    break;
                case "edit":
                    printEditMenu(e);
                    break;
            }
        }
    }

    // EFFECTS: exits program or returns to the appropriate previous menu with the selected workouts
    private void handleInput(String str, String menu, Workout w) {
        if (quitInput(str)) {
            printSaveOption();
        } else {
            System.out.println("Invalid command, please try again.");
            switch (menu) {
                case "select":
                    printSelectMenu(w);
                case "edit":
                    printEditMenu(w);
                case "workout exercises":
                    printWorkoutExercisesMenu(w);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: handles input to load or not load exercise list and workout history from save file at program start
    private void handleLoadInput() {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            switch (str) {
                case LOAD_COMMAND:
                    try {
                        exerciseList = reader.readExerciseList();
                        workoutHistory = reader.readWorkoutHistory();
                    } catch (IOException e) {
                        System.out.println("File does not exist.");
                    }
                    System.out.println("Data loaded from save file.");
                    printMainMenu();
                    break;
                case NO_COMMAND:
                    System.out.println("Data not loaded.");
                    printMainMenu();
                    break;
            }
        }
    }

    // EFFECTS: handles input based on options from the main menu, redirects to the appropriate ui
    private void handleMainInput() {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            switch (str) {
                case EXERCISE_COMMAND:
                    printExerciseOrWorkoutMenu("exercise");
                    break;
                case WORKOUT_COMMAND:
                    printExerciseOrWorkoutMenu("workout");
                    break;
                default:
                    handleInput(str, "main");
            }
        }
    }

    // EFFECTS: handles input from the exercise/workout menu, redirects to the appropriate ui
    private void handleExerciseOrWorkoutInput(String menu) {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            switch (str) {
                case VIEW_COMMAND:
                    if (menu.equals("exercise")) {
                        printExerciseListOrWorkoutHistory("exercise list");
                        if (!exerciseList.getExerciseList().isEmpty()) {
                            printExerciseListOrWorkoutHistoryMenu("exercise");
                        }
                    } else {
                        printExerciseListOrWorkoutHistory("workout history");
                    }
                    break;
                case CREATE_NEW_COMMAND:
                    create(menu);
                    break;
                case BACK_COMMAND:
                    printMainMenu();
                    break;
                default:
                    handleInput(str, menu);
            }
        }
    }

    // EFFECTS: handles input from the exercise list/workout history menu, redirects to the appropriate ui
    private void handleExerciseListOrWorkoutHistoryInput(String menu) {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            if (str.equals(SORT_BY_DATE_COMMAND) && menu.equals("workout")) {
                sortWorkouts();
            }
            switch (str) {
                case SELECT_COMMAND:
                    select(menu);
                    break;
                case FILTER_COMMAND:
                    filter(menu);
                    break;
                case BACK_COMMAND:
                    printExerciseOrWorkoutMenu(menu);
                    break;
                default:
                    if (menu.equals("exercise")) {
                        handleInput(str, "exercise list");
                    } else {
                        handleInput(str, "workout history");
                    }
            }
        }
    }

    // EFFECTS: handles input from the select exercise menu, redirects to the appropriate ui
    private void handleSelectInput(Exercise e) {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            switch (str) {
                case EDIT_COMMAND:
                    printEditMenu(e);
                    break;
                case DELETE_COMMAND:
                    deleteExercise(e);
                    break;
                case BACK_COMMAND:
                    printExerciseListOrWorkoutHistory("exercise list");
                    if (!exerciseList.getExerciseList().isEmpty()) {
                        printExerciseListOrWorkoutHistoryMenu("exercise");
                    }
                    break;
                default:
                    handleInput(str, "select", e);
            }
        }
    }

    // EFFECTS: handles input from the select workout menu, redirects to the appropriate ui
    private void handleSelectInput(Workout w) {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            switch (str) {
                case EDIT_COMMAND:
                    printEditMenu(w);
                    break;
                case VIEW_COMMAND:
                    printWorkoutExercises(w);
                    break;
                case BACK_COMMAND:
                    printExerciseListOrWorkoutHistory("workout history");
                    break;
                default:
                    handleInput(str, "select", w);
            }
        }
    }

    // EFFECTS: handles input from the edit exercise menu, redirects to the appropriate ui
    private void handleEditInput(Exercise e) {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            if (str.equals(BACK_COMMAND)) {
                printSelectMenu(e);
            } else if (str.equals(NAME_COMMAND) || str.equals(TYPE_COMMAND) || str.equals(WEIGHT_COMMAND)
                    || str.equals(SETS_COMMAND) || str.equals(REPS_COMMAND) || str.equals(REST_TIME_COMMAND)
                    || str.equals(NOTE_COMMAND)) {
                edit(e, str);
            } else {
                handleInput(str, "edit", e);
            }
        }
    }

    // EFFECTS: handles input from the edit workout menu, redirects to the appropriate ui
    private void handleEditInput(Workout w) {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            if (str.equals(BACK_COMMAND)) {
                printSelectMenu(w);
            } else if (str.equals(DATE_COMMAND) || str.equals(TYPE_COMMAND) || str.equals(LOCATION_COMMAND)) {
                edit(w, str);
            } else {
                handleInput(str, "edit", w);
            }
        }
    }

    // EFFECTS: handles input from the workout exercise menu, redirects to the appropriate ui
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
                    printSelectMenu(w);
                    break;
                default:
                    handleInput(str, "workout exercises", w);
            }
        }
    }

    // EFFECTS: handles input to save or not save exercise list and workout history to file before program ends
    private void handleSaveInput() {
        String str = getUserInputString();
        if (!str.isEmpty()) {
            switch (str) {
                case SAVE_COMMAND:
                    try {
                        writer.open();
                        writer.write(exerciseList, workoutHistory);
                        writer.close();
                    } catch (IOException e) {
                        System.out.println("Cannot save to file with invalid name.");
                    }
                    System.out.println("Data saved to file.");
                    endProgram();
                    break;
                case NO_COMMAND:
                    System.out.println("Data not saved.");
                    endProgram();
                    break;
            }
        }
    }

    // EFFECTS: stops receiving user input and prints closing message
    public void endProgram() {
        input.close();
        System.out.println("Quitting application... Thanks for using Workout Tracker!");
    }

}
