<h4> FriendHub</h4>

A Full-Stack application that was written in a reactive style and follows a microservices architecture.

Initially, I wanted to upload the application as a container on Docker Hub, but I encountered a problem with Maven, which was unable to detect one module in the local repository, preventing me from packaging the project.Installing the module in the local repository didn't work, so I eventually decided to just take screenshots and create gifs instead.




<br><br><h2>Registration-Process</h2>
The first step is the registration process. In the backend, I implemented validation to check if the inputs are valid.
![image](https://github.com/user-attachments/assets/1bd10fa2-d058-4970-b405-3e852e7a99a5)




<br><br> <h2>Login-Process</h2>
After successful registration, you can proceed to login. On the backend side, I implemented JWT, and after a successful login,
you receive a token. However, on the frontend side, I did not implement this.
![image](https://github.com/user-attachments/assets/d09dd208-7f34-43bb-8468-1edcc635f409)




<br><br><h2>Posts</h2>
You can write posts. I have implemented the 'findAll' method on the backend side, and on the home page, all the posts will be displayed.

![image](https://github.com/user-attachments/assets/b6e31f35-5db8-4c63-94fb-f8a07689bcf9)





<br><br><h2>Comments</h2>

Once you click on a post, you will be redirected to the post details page, where you can see all the comments related with the specific post.
<br>You can write comments and response to other comments.

![image](https://github.com/user-attachments/assets/885024a9-80d3-4b7b-8693-ff8848ce785b)



<br><br> <h2>Friends</h2>
You can add to friend or remove depending on if user is currenty on your friendList.
![image](https://github.com/user-attachments/assets/70cfc394-df8d-4d9b-9c48-7306fe38d6fd)


<br><br><h2>Real-Time Messaging</h2>
I implemented WebSockets on the backend side, which are responsible for sending messages in real-time.
![webstorm64_rq8hfpT7Ih](https://github.com/user-attachments/assets/e2142e01-05f5-4a9f-b7f1-a24527ee048c)




<br><br><h2>User Availability</h2>
There is also posibility to check whether user is online or offline.
Very handy here became the 'SessionConnectedEvent,' which I used in event listeners to check whether a user has connected to or disconnected from StompJS.
![msedge_491GLTYdwN](https://github.com/user-attachments/assets/704f98c4-51ac-4f0f-83e4-a422708e4ba3)



<br><br><br>

<h1>Backend</h1>
<h2>In this section, I will briefly discuss few tools/modules in my code</h2>




<h3><b>AuthService</b></h3> I used this module to integrate Spring Security, JWT, and route other service ports through the API Gateway.Api Gateway fetches ports through the eureka. The entire implementation is written reactively.


<h3><b>RabbitMq</b></h1>Modules are communicating with each other via rabbitmq.I used mainly rabbit just for delivering userData. For example PostService communicates with userService when he
needs data about the user.


<h3> Redis </h3> I used the tool to dynamically manage user sessions. Once a user logs in, his username and session id is saved as a hash map in Redis. When the user leave the application, his session is removed.


//od tąd
<h3>Testing</h3>
<h1>Frontend</h1>
Frontend is written using angular.I wasn't focussed on writing frontend that much. It was written just to endeepen into frontend tools    then there
is stil  many things to change but the application work's as it should.

Below is a link to frontend repository
https://github.com/kamiloses/FriendHub-Frontend
//do tąd


<br><br><br><br><br>
<h4>libraries/frameworks used :</h4>

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




