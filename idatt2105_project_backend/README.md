# SnagIt Backend

SnagIt is a web-application for selling and buying items. This project contains the code for the server.

## Members
- Therese Synnøve Rondeel
- Carine Margrethe Rondeel

## Prerequisites (and recommended versions)
- Java (21+)
- Maven (3.9.6+)
- MySQL (and recommend MySQL Workbench)

## Before running the server
Before running the server you should go to src/main/resources/application.properties. Inside this file you should change the username and password to your personal ones. You should ensure that these correspond to the MySQL-erver running on *localhost:3306*, since this is the IP-address both the application database and test database refer to. This should be the default when setting up MySQL on your computer.

Be aware that, due to project requirements, test data is added to the database every time when starting the server. The tables will therefore be dropped and renewed again each time the server is run to avoid duplicates.

```
spring.datasource.username=root
spring.datasource.password=password
```

## Starting the server
To start the server enter this into the terminal in your IDE while in the project directory. We recommend using Intellij.

```
mvn spring-boot:run
```

## Run tests
To run the tests enter this into the terminal in your IDE while in the project directory. We recommend using Intellij.

```
mvn test
```

## Filesystem for pictures
To store the pictures related to the listings, a 'filesystem' folder has been added, and is located at the same level as 'src'. Pictures used with listings will be saved to 'filesystem/uploads'.

As mentioned earlier, due to the project requirement to include test data, the database tables are dropped and renewed every time the server is run. When using the application, one has the ability to delete a listing, which will also delete the corresponding picture. We have therefore included a folder with backups, found at 'filesystem/backups'. Here one may copy and paste the deleted pictures if rerunning a server at a later time

We acknowledge that this is not ideal, but it is our current best effort.


## Swagger

To view the endpoint documentation, start the server and visit this site: http://localhost:8080/swagger-ui/index.html#/

