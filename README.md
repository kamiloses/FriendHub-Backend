
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



![msedge_491GLTYdwN](https://github.com/user-attachments/assets/704f98c4-51ac-4f0f-83e4-a422708e4ba3)

<br><br><br>

<h1>Backend</h1>
<h2>In this section, I will briefly discuss few tools/modules in my code</h2>



<h3><b>AuthService</b></h3> I used this module to integrate Spring Security, JWT, and route other service ports through the API Gateway.Api Gateway fetches ports through the eureka. The entire implementation is written reactively. If any other module requires data about the connected user, it can request it from AuthService via RabbitMQ.






<h3><b>RabbitMq</b></h1>Modules are communicating with each other via rabbitmq.I used mainly rabbit just for delivering userData. For example PostService communicates with userService once he
need data about the user.


<h3>Webflux</h3>



<h3>Testing</h3>

<h1>Frontend</h1>
Frontend is written using angular.I wasn't focussed on writing frontend that much. It was written just to endeepen into frontend tools    then there
is stil  many things to change but the application work's as it should.

Below is a link to frontend repository

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
- Redis
- Junit
- Mockito




