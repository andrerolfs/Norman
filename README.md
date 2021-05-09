# Idea Of Database Model

1. The format for coding entity data is using URL parameters
2. An entity consists of n keys and 1 value   
2. Keys are interpreted as hierarchical connections 
3. entities are added in order, never replaced 

Examples for single value :

    KEY1 = checkWeatherJob 
    KEY2 = 19 
    KEY3 = Job Result
    VALUE = passed

    KEY1 = checkWeatherJob 
    KEY2 = 19 
    KEY3 = Tomorrow´s Weather
    VALUE = sunshine

    KEY1 = computeJob 
    KEY2 = 27
    KEY3 = Job Result
    VALUE = passed

    KEY1 = computeJob 
    KEY2 = 27
    KEY3 = Computation Result
    VALUE = 528491
   
    KEY1 = computeJob 
    KEY2 = 28
    KEY3 = Job State
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
   