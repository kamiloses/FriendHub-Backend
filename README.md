POPRAW DOKŁADNIE POTEM

<h4> FriendHub</h4>

FullStack application which was written in reactive style and in microservices architechture.
  

Initially i wanted to  upload the applicaton as container on dockerhub but i was having problem with maven which was unable to detect one module in local repository and i was not able to  
package the project .Eventualy i decided to make only screenshots.

The first step is registeration process. in the backend i implemented validation which checks if inputs are valid.
REGISTRATION IMAGE


after successfull registration you can procced to login 

LOGIN IMAGE


you can write posts.I have implemented on the backend side "findAll" then on home side will shown all the posts .

HOME SIDE

once you click the post you will be moved to postDetails where you can see all the comments related with specific post  
POSTDETAIL IMAGE


You can write comments and response to other comments
COMMENTS IMAGE


you can add to friend or remove depending on if user is currenty on your friendList


I implemented on the backendSide websockockets which are responsible for writing messages in real time
WRTING IN REALLIFE IMAGE


And there is also posibility to check whether user is online or offline.
Very handy becomed here "SessionConnectedEvent" which i used in eventListeners to check whether user has connected to StompJS or dissconnected




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




