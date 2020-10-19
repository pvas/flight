# Immfly Professional Selection

# Description

- A microservice that has the responsibility of retrieving all flight information through an external service and storing it in a Redis database, so that we can have a cache system to manage all flight information and use it in our other microservices.

### Tech

The application is build using Spring Boot framework (2.3.4.RELEASE) with the following libraries:

*	[Spring Web 2.3.4.RELEASE]: In order to create endpoint available to the final user
*	[Spring Redis Data 2.3.4.RELEASE]: Spring data to Redis
*	[Redis Client Jedis]: Client for Redis
*	[Spring Security]: Spring Security
*	[Lombok 1.18.12]: to avoid the boiler plate when we are creating the setter and getters method
*	[Jacksondatabind 2.11.0]: Convert that data from json format to java objects format
*	[Junit: 5.6.2]: framework that was used to create unit test for the application 
*	[Mockito 3.3.3]: library that allows us to mock and validate some methods call in unit tests
*	[springfox-swagger2 3.0.0-SNAPSHOT]: simplify API development with the Swagger open source and professional toolset.
*	[Apache commons-pool2]: library to provide the object pooling API.

Everything managed with Maven, using Java 8.

### Installation

Compile it using Maven

```sh
$ cd flight
$ mvn clean install
$ java -jar target/flight-1.0.0-SNAPSHOT.jar
```

### Dockerfile

Need the Docker installed.

```sh
docker pull redis
docker run -p 6379:6379/tcp -d -v $PWD/redis-data:/ubuntu/redis/data --name redis_cont redis:latest 

$ cd flight
docker build -t flight .
docker run -p 8080:8080/tcp --name flight_cont flight:latest
```

### Consuming

Postman URL:
http://localhost:8080/v1/flight-information/{tail-number}/{flight-number}
