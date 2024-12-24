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
Home landing page for the application, choices of `Add Listening Session`, `Edit Listening Session`, `Delete Listening Session` and `View Listening Session`.

![EarTrackerMainPage.png](ImagesREADME%2FEarTrackerMainPage.png)

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

### DynamoDb Database 
DynamoDb is the database that stores all of the `Listening Session` datapoints, when called by the other API endpoints will pull datapoints from the dynamoDb table and modify/display accordingly. 

![DynamoDbExample.png](ImagesREADME%2FDynamoDbExample.png)
