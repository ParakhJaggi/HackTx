# Readme
This is the hacktx sample application.

## Elastic Search
This application uses elastic search as a data backend. To run the app you will have to either install elastic search locally or point to a remote elastic search instance. Configuration options are provided in `application.yml`.

## Development
In development there are three components to the running application. A Java app server, which runs your backend code, elasticsearch, which is the data store, and a webpack development server which serves up the frontend resources and does hot reloading.

### Running the Backend in IntelliJ
1) Install Java, Gradle, IntelliJ if you don't already have them
1) Install and start elasticsearch
1) Import the project into IntelliJ
1) Run `HackTxApplication.main` with VM args `-Dspring.profiles.active=development`
    * Open hacktxApplication.java, right click, and Run hacktxApplication.main(), which should fail
    * Up near the top left you should see a dropdown that now says hacktx application, click it and select Edit configurations
    * In the menu, you should see a VM Options box, enter `-Dspring.profiles.active=development`, click OK
    * Run the application again, and it should start correctly
1) Go to `http://localhost:8080/` and verify the application is running (you may see a blank page if the frontend isn't running yet, but if it doesn't 404 you are good)

### Running elasticsearch
1) Install elasticsearch 6.0.1 https://www.elastic.co/downloads/past-releases/elasticsearch-6-0-1
2) Run `elasticsearch` and confirm it started with `curl -XGET 'localhost:9200'`

### Running the Frontend
1) Install node if you don't already have it
1) In the project directory run `npm install` from the command line
1) In the project directory run `npm run dev` from the command line
1) Go to `http://localhost:8080/` - you should see simple output which reads "This is the home page."
1) Navigate to page 1 and click the "Login as User" button - you should see a token print and the text "Welcome, user!" show on the page. This means everything is working.

## QA/Heroku/Running as QA Locally
In qa there is just a single executable jar which contains the static resources produced from webpack.

1) To build run `gradle install`
1) Build output can be found in `<Root Project Dir>/build/libs/hacktx-site-1.0-SNAPSHOT.jar`
1) This jar is all you need to run the application, to run locally use the following command: `java -jar build/libs/hacktx-site-1.0-SNAPSHOT.jar --spring.profiles.active=qa`
