# My Personal Project: Workout Tracker

## Introduction

The application will track workouts, including entries of exercises and information such as:
- name
- exercise type
- weight
- sets
- reps
- rest time

It will show the *details* of a workout as well as a *history* of past workouts, 
so that users would be able to note their progress over time. 
This application would be helpful for gym rookies, seasoned fitness enthusiasts, or anyone who enjoys working out.
<br> <br>
As someone who currently likes to keep a log of all my workouts in a spreadsheet, 
I wanted to track them in a format that is **accessible, well-organized, and simple to use**.
I hope to create an application that can allow me to easily add my workouts and see my progress. 

## User Stories
As a user, I want to be able to:
- add an exercise to my workout, and specify the exercise details of name, exercise type, weight, set, reps, and rest time
- add a workout to my workout history, and specify the date, workout type, and location
- view the list of all exercises that have been created, and be able to filter by exercise type
- view the list of workouts in my workout history, and be able to sort by date or filter by workout type
- select a workout in my workout history and view its details and its exercises
- select a workout or an exercise and add to or edit the details of the workout or the exercise

### Data Persistence User Stories
As a user:
- when I quit the application, I want to be given the **option** to save my exercises and workouts to file 
- when I start the application, I want to be given the **option** to load my exercises and workouts from file

## Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by
clicking the "Create" button in either the Exercise or Workout Menu, filling the fields as desired,
and confirming using the "Create!" button in the dialog.
- You can generate the second required action related to the user story "removing an X from a Y" by
clicking on an exercise or a workout that is a row in the table shown in either the Exercise or Workout Menu, 
then clicking the "Delete" button, and confirming using the "Yes" button in the dialog.
- You can locate my visual component by looking at the icons on the "Create", "Edit", and "Delete" buttons.
- You can save the state of my application by closing the window and selecting "Yes" in the save option dialog.
- You can reload the state of my application by launching the application and selecting "Yes" in the load option dialog.

## Phase 4: Task 2
- If the user chooses to load from save file when the program first opens, 
the loaded exercises/workouts appear as "exercise added to exercise list"/"workout added to workout history" events,
with corresponding names of the exercise/workout in the event description as well.
- Whenever the user creates an exercise/a workout, 
there is an event ("exercise added to exercise list"/"workout added to workout history") as appropriate.
- Whenever the user deletes an exercise/a workout,
there is an event ("exercise removed from the exercise list"/"workout removed from the workout history") as appropriate.
- When the user quits the program, the event log containing all the events since program run are printed on the console,
then the program closes.

## Phase 4: Task 3
If I had more time to improve my design, I would create a new class (e.g., AllExercises) 
to store a list of all created exercises. 
This class would either extend ExerciseList or have a field of type ExerciseList, 
so it can use the same methods as ExerciseList. 
AllExercises would use the Singleton pattern so that there is only one instance of this master list of exercises, 
which is different from the multiple ExerciseList objects that are instantiated by each Workout object, 
as each Workout object currently has an ExerciseList field. 
I would also make WorkoutHistory a Singleton class, since there should only be one instance in the program.
<br> <br>
The Tracker (console-based UI) and TrackerGUI (graphical UI) would then be able to remove their associations 
with ExerciseList and WorkoutHistory. 
Tracker and TrackerGUI could instead access the one AllExercises instance and the one WorkoutHistory instance
with a getInstance method, since they are both now Singleton classes.
This change would allow for Tracker and TrackerGUI to no longer be coupled 
with the chain on associations between Exercise, ExerciseList, Workout, and WorkoutHistory,
which reduces coupling in the overall project.
