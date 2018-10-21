# [Garlic Gambler](https://github.com/ParakhJaggi/HackTx)
This is a web based blackjack game called Garlic Gambler. Users can play against the house in blackjack and bet with Garlicoin.

This web application was made during HackTX 2018.

Tech stack: Node.js, React, Java, and Elasticsearch.

Made by (GitHub links):
 * [Sean Blonien](https://github.com/sblonien)
 * [Parakh Jaggi](https://github.com/ParakhJaggi)
 * [Neil Wilcoxson](https://github.com/neilwilcoxson)
 * [Mark Du](https://github.com/mdu2017)
 * [Caleb Dehaan](https://github.com/calebdehaan)

 
## To Run the App Locally
There are three components to the running application. A Java app server, which runs your backend code, elasticsearch, which is the data store, and a webpack development server which serves up the frontend resources and does hot reloading.

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
1) Install elasticsearch 6.4.2
2) Run `elasticsearch` and confirm it started with `curl -XGET 'localhost:9200'`

### Running the Frontend
1) Install node if you don't already have it
1) In the project directory run `npm install` from the command line
1) In the project directory run `npm run dev` from the command line
1) Go to `http://localhost:8080/`
1) The website is good to go! If you followed every step so far without errors, this will work!
