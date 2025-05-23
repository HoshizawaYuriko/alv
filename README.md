# DHBW Advanced Software Engineering Projekt: Anime-Listen-Verwaltungstool (ALV)
Implemented as an REST API using Spring Boot

## Running the Project
Run Spring Boot by using the following command in the project folder:
```bash
mvn spring-boot:run
```

## Database
Access the H2 in-memory database via http://localhost:8080/h2-console/

Username and password can be found in the [application.properties](https://github.com/HoshizawaYuriko/alv/blob/main/src/main/resources/application.properties) file.

## Accessing the API Endpoints
Import the [ALV_API.postman_collection.json](https://github.com/HoshizawaYuriko/alv/blob/main/ALV_API.postman_collection.json) file in Postman to use a predefined set of Requests for testing.
