# Social-app

Minimal [Spring Boot](http://projects.spring.io/spring-boot/) sample app.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 4](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `de.codecentric.springbootsample.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

# Api Collection

- [Postman Collection Link](https://www.getpostman.com/collections/05c06ee7aa225f544ea9)
- List
  - https://social-app-noman.herokuapp.com/api/user/signup
  - https://social-app-noman.herokuapp.com/api/user/authenticate
  - https://social-app-noman.herokuapp.com/api/post/add
  - https://social-app-noman.herokuapp.com/api/user/logout
  - https://social-app-noman.herokuapp.com/api/post/get/{username}
  - https://social-app-noman.herokuapp.com/api/follower/add
  - https://social-app-noman.herokuapp.com/api/user/all


# Test Server 

- [Test url](https://social-app-noman.herokuapp.com/api/user/test)
- This will give ![img.png](img.png)
- Response of test user's data


