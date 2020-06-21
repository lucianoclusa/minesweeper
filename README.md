# minesweeper-API

## Technologies
* Java11
* SpringBoot 2.4
* Spring-data-dynamodb 5.1.0
* JUnit5
* Open API

## Project Structure
I used DDD pattern to design this API. The project is divided in 3 main layers: application, domain, infrastructure.
* Domain: This layer is where the game logic is. It is completely isolated from any framework it is pure and only how the minesweeper works.
* Application: This layer acts as a facade through which clients will interact with the domain model.
* Infrastructure: This layer is where most of the frameworks configurations are.

## Important notes
OpenAPI was used on this project so you may find all exposed endpoints with useful examples in this url:

http://api-prod.eba-pziz29mt.us-east-2.elasticbeanstalk.com/minesweeper-api/swagger-ui.html

You can also find a client lib made in Javascript under the directory /client.

This exercise took me a little bit more time than I expected because I took the time to read about AWS specially ElasticBeanstalk, DynamoDB and Cognito.
I could use the first two services as  you can see, sadly I could not implement Cognito with Springboot security as I wished I could on the given time.

Hope it is not too bad :) 