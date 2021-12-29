# Idea Of Entity Database Model

1. The format for coding entity data is using URL parameters
2. An entity consists of n keys and 1 value   
2. Keys are interpreted as hierarchical connections 
3. entities are added in order, never replaced 

Examples for single value :

    KEY01 = checkWeatherJob 
    KEY02 = 19 
    KEY03 = Job Result
    VALUE = passed

    KEY01 = checkWeatherJob 
    KEY02 = 19 
    KEY03 = Tomorrow´s Weather
    VALUE = sunshine

    KEY01 = computeJob 
    KEY02 = 27
    KEY03 = Job Result
    VALUE = passed

    KEY01 = computeJob 
    KEY02 = 27
    KEY03 = Computation Result
    VALUE = 528491
   
    KEY01 = computeJob 
    KEY02 = 28
    KEY03 = Job State
    VALUE = started

enables you to 

show values for all jobs with all IDs :

    checkWeatherJob | 19 | Job Result           | passed
    checkWeatherJob | 19 | Tomorrow´s Weather   | sunshine
    computeJob      | 27 | Job Result           | passed
    computeJob      | 27 | Computation Result   | 528491
    computeJob      | 28 | Job State            | started

show values only for all build IDs of the selected job

    computeJob 

    27 | Job Result           | passed
    27 | Computation Result   | 528491
    28 | Job State            | started
   
3. show values only for the selected build of the selected job


    computeJob 27
   
    Job Result           | passed
    Computation Result   | 528491

# ToDo List Idea

The idea is to have a model which is flexible enough as todo list.

Maybe a Kanban system is implemented. 

Teams could need this.

1. a user owns multiple todo lists
2. a todo list can have items
3. a user owns these items
4. multiple users can share a todo list
5. users can re-use all items from lists they own
6. a user gains new items from other users when they share their lists
7. when a user changes his own item, it is changed
8. when a user changes the item owned by an other user, a new item is created
9. when a user his own item on a shared list, the other sharing users get new copies of the changed item
10. 
