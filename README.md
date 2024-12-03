<h3>This project has been systematically upgraded since November 6th but is not finished yet. Therefore, I recommend checking out this application:
https://github.com/kamiloses/FriendHub-Deprecated-Version
  
Alternatively, you can check the current status of the project by reading the README or exploring the code, which has improved a lot in comparision to my previous project.</h3>




<br><br><br>

<h1>Backend</h1>
<h2>In this section, I will discuss my backend services.</h2>

<h3><b>AuthService</b></h3> I used this module to integrate Spring Security, JWT, and route other service ports through the API Gateway. The entire implementation is written reactively. If any other module requires data about the connected user, it can request it from AuthService via RabbitMQ.
<h3><b>CommentService</b></h3> This module is created for writing and displaying comments. Currently, it only supports displaying comments related to posts.
I integrated the Eureka Discovery Client into almost every module to help the API Gateway route the ports.

<h3><b>EurekaServer</b></h3> A simple module which contains only Eureka server. It is responsible for enabling the server.
<h3><b>FriendService</b></h3> This module stores data about friendships between two users. I created an entity that collects two users: one as the person who sent the friend request and the other as the recipient of the invitation.
<h4>MessageService</h4> This module manages messages related to users' chats.

<h4>PostService</h4> This module stores data about posts. Currently, it supports creating and displaying posts.

<h4>UserService</h4> This module contains data about users. Every other service needs access to this one.




<h1>Frontend</h1>
![image](https://github.com/user-attachments/assets/3b82e4ba-526f-4357-9919-b1e2951f96ad)




<br><br><br><br><br>
<h4>Currently Used:</h4>

- Java 17
- Spring boot 3
- Project Reactor
- Reactive MongoDb
- RabbitMq
- Eureka Discovery Server & client
- Angular
- Reactive Api gateway
- Srping security
- JWT
- Lombok




