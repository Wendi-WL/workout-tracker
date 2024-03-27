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