# Ear Tracker
## An NSS Capstone Project by Myles Wang

### https://d7blnv3vyf8p9.cloudfront.net

This is my final capstone project from NSS. It was created following the guidelines of the course rubric to showcase and incorporate a solid understanding of Java, back-end concepts, AWS software services and cloud technologies learned during the program.

## Use Case 
Ear Fatigue is a phenomenon an individual may experience after being exposed to long sessions of audio, sound, and noise, Although not clinically proven, researchers suggest it may lead to fatigue, overall tiredness, and increased anxiety and stress levels if left unaddressed. 

This is also a very important factor for audio professionals and musicians, as extended sessions of listening time will decrease the sharpness and focus of the ear, causing errors and inaccuracies when working. 

This application allows the users to store, track, and analyze their listening activity, comparing their ear-fatigueness over time and providing users recommendations on how much rest they need to achieve a healthy audio lifestyle.

### Used AWS Technologies 
- DynamoDb Database Tables 
- Cloudformation Cloud Infrastructure 
- S3 Bucket Scalable Storage
- Cognito Login Access Management

### Main Page
Home landing page for the application, choices of `Add Listening Session`, `Edit Listening Session`, `Delete Listening Session` and `View Listening Session`, as well as login and logout user access functions.

![EarTrackerMainPage.png](ImagesREADME%2FEarTrackerMainPage.png)

### Cognito Access Management
AWS Technology that links user with their data, uses an email address + password as the login ID key matching user to their accounts.

![CognitoLoginExample.png](ImagesREADME%2FCognitoLoginExample.png)

### Add Listening Session
Adds a new `Listening Session` datapoint into the database. 

![AddListeningSessionPage.png](ImagesREADME%2FAddListeningSessionPage.png)

### Edit Listening Session
Finds an existing `Listening Session` datapoint and updates the contents of it.

![EditListeningSessionPage.png](ImagesREADME%2FEditListeningSessionPage.png)

### Delete Listening Session 
Finds an existing `Listening Session` datapoint and remove it from the database.

![DeleteListeningSessionPage.png](ImagesREADME%2FDeleteListeningSessionPage.png)

### View Listening Session
Displays all existing `Listening Session` data-points within a database and given parameters (`Date` or `Type`).

![ViewListeningSessionPage.png](ImagesREADME%2FViewListeningSessionPage.png)

### Date Variable
Using java.lang.object LocalDateTime as date variable, Front-End uses calendar model to input LocalDateTime variable data.

![DateExample.png](ImagesREADME%2FDateExample.png)

### DynamoDb Database Tables 
DynamoDb is an AWS technology that stores all of the `Listening Session` data-points in a structured database, and when called upon will pull the data-points from the database table to the API for the user. 

![DynamoDbExample.png](ImagesREADME%2FDynamoDbExample.png)
