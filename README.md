
Currently Used:


Java 17
Spring boot 3
Project Reactor
Reactive MongoDb
RabbitMq
Eureka Discovery Server & client
Angular
Reactive Api gateway
Srping security
JWT
Lombok

<h3><bold>AuthService</bold></h3>
I used this module to integrate Spring Security, JWT, and route the other service ports through the API Gateway. Of course, the entire implementation is written in a reactive way. If any other module needs data about the connected user, they can request it from AuthService via RabbitMQ.

<h3><bold>CommentService</bold></h3>

This module is created for writing and displaying comments. I integrated in many modules like this,  Eureka Discovery Client, which helps the API Gateway route the ports.


<h3><bold>EurekaServer</bold></h3>
A simple module which contains only Eureka server. It is responsible for enabling the server.


<h3><bold>FriendService</bold></h3>
This module contains data about friendship between 2 users . I created an entity that store 2 users - one is a person who invited to friend , the second one is a person who was invited.


<h4>MessageService</h4>



