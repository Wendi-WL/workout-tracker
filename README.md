# Workout Tracker Personal Project

## Introduction

### Motivation
The application tracks workouts, including entries of exercises and information such as name, exercise type, weight, sets, reps, and rest time.
It shows the *details* of a workout as well as a *history* of past workouts, so that users can note their progress over time. 
This application would be helpful for gym rookies, seasoned fitness enthusiasts, or anyone who enjoys working out.

As someone who currently likes to keep a log of all my workouts in a spreadsheet, 
I wanted to track them in a format that is **accessible, well-organized, and simple to use**.
I created this application so that I can easily add my workouts and see my progress. 

### Technical Information
The application is coded in Java and uses test-driven development. 

It includes a console-based user interface and a graphical user interface (GUI). 
The GUI is built using components from the Java Swing framework.
See section "User Interfaces" for details about the two different UIs. 

## About this Project - User Stories
As a user, I want to be able to:
- create an exercise and add it to my workout
  - specify the exercise details of name, exercise type, weight, set, reps, and rest time upon creation
- create a workout and add it to my workout history
  - specify the workout details of date, workout type, and location upon creation
- view the list of all exercises that have been created
  - filter by exercise type
- view the list of workouts in my workout history
  - sort by date
  - filter by workout type
- select a workout in my workout history
  - view its details
  - view its exercises
    - add exercises to the workout
    - remove exercises from the workout
- select a workout or an exercise
  - add to or edit the details of the workout or the exercise

### Data Persistence User Stories
As a user:
- when I quit the application, I want to be given the **option** to save my exercises and workouts to file 
- when I start the application, I want to be given the **option** to load my exercises and workouts from file

## User Interfaces
The project currently contains both a console-based User Interface (UI) and a Graphical User Interface (GUI). 
The code launches the GUI by default when run, but to switch to the console-based version of the application,
one can uncomment the appropriate lines in the Main.java file in the ui directory and comment out the line that instantiates the GUI.

### Console-based User Interface (UI) Application
All of the user stories have been realized in the UI, including the data persistence user stories.

### Graphical User Interface (GUI) Application
Most of the user stories have been realized for the GUI application, with the exception of a few features that have not yet been added (denoted in bold):
- create an exercise and add it to my workout
  - specify the exercise details of name, exercise type, weight, set, reps, and rest time upon creation
- create a workout and add it to my workout history
  - specify the workout details of date, workout type, and location upon creation
- view the list of all exercises that have been created
  - **filter by exercise type**
- view the list of workouts in my workout history
  - **sort by date**
  - **filter by workout type**
- select a workout in my workout history
  - view its details
  - view its exercises
    - add exercises to the workout
    - remove exercises from the workout
- select a workout or an exercise
  - add to or edit the details of the workout or the exercise
    
Both of the data persistence user stories have been realized in the GUI.

### Instructions for Use
The console-based UI can be operated following the instructions and prompts printed on the console.

The GUI can be less intuitive to use. Below is a list of actions that can be taken:
- To navigate to the Exercise Menu or the Workout Menu,
click its respective tab (labelled "Exercise" or "Workout") on the navigation bar.
- To add an exercise to the exercise list or a workout to the workout history,
click the "Create" button in either the Exercise or Workout Menu, fill the fields as desired,
and confirm using the "Create!" button in the dialog.
- To remove an exercise from the exercise list or a workout from the workout history,
click on the row of the exercise or workout in the table shown, 
then click the "Delete" button, and confirm using the "Yes" button in the dialog.
- To edit an exercise or a workout, click on the row of the exercise or workout in the table shown,
then click on the "Edit" button, edit the fields as desired, and confirm using the "Save Edits" button in the dialog.
- To view the exercises in a workout, click on the row of the workout in the table shown,
then click on the "View/Add/Remove Exercises" button.
  - A new dialog will appear, showing the details of the workout along with its exercises.
    Exercises can be added or removed from the workout in a manner similar to the way exercises and workouts are created or deleted.
- To save the state of the application, select "Yes" in the save option dialog that appears upon closing the application's window.
- To reload the state of the application, select "Yes" in the load option dialog that appears upon launching the application.

## Event Logs
Event logging is an additional functionality of the application. Below is a description of the events that are logged:
- If the user chooses to load from save file when the program first opens, 
the loaded exercises/workouts appear as "exercise added to exercise list"/"workout added to workout history" events,
with corresponding names of the exercise/workout in the event description as well.
- Whenever the user creates an exercise/a workout, 
there is an event ("exercise added to exercise list"/"workout added to workout history") as appropriate.
- Whenever the user deletes an exercise/a workout,
there is an event ("exercise removed from the exercise list"/"workout removed from the workout history") as appropriate.

When the user quits the program, the event log containing all the events since program run are printed on the console,
then the program closes.

## Future Improvements
If I had more time to improve my design, I would create a new class (e.g., AllExercises) to store a list of all created exercises. 
This class would either extend ExerciseList or have a field of type ExerciseList, so it can use the same methods as ExerciseList. 
AllExercises would use the Singleton pattern so that there is only one instance of this master list of exercises, 
which is different from the multiple ExerciseList objects that are instantiated by each Workout object, 
as each Workout object currently has an ExerciseList field. 
I would also make WorkoutHistory a Singleton class, since there should only be one instance in the program.

The Tracker (console-based UI) and TrackerGUI (graphical UI) would then be able to 
remove their associations with ExerciseList and WorkoutHistory. 
Tracker and TrackerGUI could instead access the one AllExercises instance and the one WorkoutHistory instance
with a getInstance method, since they are both now Singleton classes.
This change would allow for Tracker and TrackerGUI to no longer be coupled 
with the chain on associations between Exercise, ExerciseList, Workout, and WorkoutHistory,
which reduces coupling in the overall project.

## Acknowledgements
Parts of the code in the the EventLog and Event model classes, persistence classes, and Tracker and TrackerGUI UI classes 
were modelled after the code from repositories provided by the university course for which this project was first created.

Due to course policy, references to the course name have been removed from this project, 
so these repositories cannot be explicitly linked in this section. Nonetheless, I would like to provide an attribution.
