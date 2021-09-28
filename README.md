User RESTful API Service
==================

The user RESTful Api is build using the following technologies with swagger-ui and in-memory database. 

1. [Maven](https://maven.apache.org/)
1. [Spring Boot](https://spring.io/projects/spring-boot)

### Build the service
Make sure you have installed maven in your environment. 

In the root directory of this project, run
```bash
mvn spring-boot:run
```
The application will run on port `8080`.

The in-memory database also has a web console enable. You can go to the following path to check the database.
`localhost:8080/h2`.

###Endpoint
This service will allow basic CRUD operation, with uri prefix `/users`

* CREATE: `/users` with `email` and `password` as required fields in request body.
* UPDATE: `/users/{email}` with `email` and `password` as required fields in request body.
* GET: `/users/{email}`
* DELETE: `/users/{email}` 