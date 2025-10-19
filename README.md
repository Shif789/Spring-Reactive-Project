# reactive-spring-webflux
Spring Webflux

This is a multi-module project that allows users to manage movie reviews.
It consists of two microservices movie-info-service and movie-review-service and a main service that communicates with them via external API calls to produce a combined response object.

1) movie-info-service: Handles CRUD operations related to movie information.

2) movie-review-service: Handles CRUD operations related to movie reviews.

3) movie-service (main service): Uses WebClient to call the APIs of both microservices and aggregate their responses.

🪜 Steps to Run the Project

1) Download the ZIP file or clone the repository from my GitHub.

2) Install MongoDB and start the MongoDB instance.

3) Run the following services in order:

   1) movie-info-service
   2) movie-review-service 
   3) movie-service

4) You can then execute the cURL commands provided inside movies-service/src/main/resources,
or use Postman/Insomnia to make API calls.

Swagger documentation is not yet implemented.