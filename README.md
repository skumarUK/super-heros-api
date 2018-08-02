# SUPERHERO WS
---
**SuperheroAPI** is a REST/JSON web service implemented in Java using the framework Spring Boot. It implements the Spring Security Basic authorization method and supports the following:  

- Create a superhero
- Get a list of all superheros
- Get the details of a specific superhero
  
More Info - **https://<hostname>/docs/index.html**  

## API Deployment

###Pre-requistics

1. Docker is up and running.
2. Apache Maven 3.x is installed.

SuperheroAPI source code can be found at - https://bitbucket.org/skumar_wex/payworks/

###Build process with docker

1. **mvn clean package** [this command will package **SuperheroAPI** and will build docker images for db and api.] 
2. **docker-compose up -d** [startup the docker containers  for **SuperheroAPI** db and api. Note: this will run docker containers in background if you want to watch server logs then run without ***-d*** parameter]
3. Access **SuperheroAPI** at **https://<hostname>/docs/index.html** 

###Build process without docker

If you want to build  **SuperheroAPI** without docker.

1. Change below to true at superherows\pom.xml
		<docker.skip>true</docker.skip>
2. Provide DB server details at superherows\superhero-api\src\main\resources\application.properties
3. mvn clean package 
4. java -jar superhero-api-1.0.0-SNAPSHOT.jar
5. Access **SuperheroAPI** at **https://<hostname>/docs/index.html**		


## Authentication - HTTP Basic Authentication

Superhero API uses HTTP Basic Authentication. Currently, this API is configured for access to below users. 

1. **User** - **superhero_user_access**
2. **Admin User** - **superhero_admin_access**

### Usage

To Learn more about **SuperheroAPI** visit **https://<hostname>/docs/index.html**

## Technologies Used

1.  Spring Boot 2.0.0
2.  Spring Rest Core - REST API Build
3.  Spring Rest Docs - API documentation
4.  Spring Security - securing api and authorization.
5.  Hibernate 5.x - ORM for DB interactions
6.  HTTPS - API is only accessible via HTTPS.
7.  MYSQL 5.6 - API Database
8   H2 1.4.194 - API Testing Database
9.  JDK 8 - Java language
10. Tomcat - 8.5.28 - Spring Boot - embedded application server.
11. Docker - dockerized application deployment.