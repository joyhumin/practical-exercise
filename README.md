User RESTful API Service
==================

The user RESTful Api is built using the following technologies with swagger-ui and in-memory database. 

1. [Maven](https://maven.apache.org/)
1. [Spring Boot](https://spring.io/projects/spring-boot)

### Build the service
Make sure you have installed maven in your environment. 

Clone this project, then in the root directory of the project, run
```bash
mvn spring-boot:run
```
The application will run on port `8080`.

The in-memory database also has a web console enable. You can go to the following path to check the database.
`localhost:8080/h2`.

### Endpoint

The root endpoint is not defined yet. Please go to `localhost:8080/swagger-ui` to use the service.

This service will allow basic CRUD operation, with uri prefix `/users`

* CREATE: `/users` with `email` and `password` as required fields in request body.
* UPDATE: `/users/{email}` with `email` and `password` as required fields in request body.
* GET: `/users/{email}`
* DELETE: `/users/{email}` 

## Achievements

* Fully functional Restful api which allows simple CRUD operations
* Validation for request body: make email and password as required fields. Also, using `@Email` annotation to validate user input is actually email address
* Error handling for both business logic level and user's illegal arguments
* Unit testing for controller layer and service layer
* Swagger UI for api interface
* Using MVC and repository design pattern for the application structure

## Things to improve

* Refactoring the code to make it more concise
* Adding Java inline documentation
* Maybe consider encrypt `password` field rather than store it as it is, as it is not good practice 
* Initialising dataset so that there is test data to play around initially


## Comments

This is my very first project using Spring Boot framework in Java after my in-class Java project back to 2020. 

Due to my workload from uni, I only started working on this project on 28th Sept, 2021. I spent 20 hours in total to learn, build and test this application from scratch.

This has been a great learning experience, and I am grateful that Vector could give me this opportunity. 

