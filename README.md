<h1>FriendHub</h1>
<h3>
A full-stack application developed in a reactive style with a microservices architecture.
</h3>



<br><br>
<h3>Important - The project is not finished yet. I am working to make this app fully resemble Facebook/Twitter and after that, I will upload it. Right now, you can check the current status of my project, where the most important features have been implemented.</h3>
<p>

</p>


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

Once you click on a post, Angular will send a request to the backend to fetch comments related with the post, and you will be redirected to the post details page, where you can view all the comments associated with that specific post.
<br>You can write comments and response to other comments.

![image](https://github.com/user-attachments/assets/885024a9-80d3-4b7b-8693-ff8848ce785b)



<br><br> <h2>Friends</h2>
You can add to friend or remove depending on if user is currenty on your friendList.
![image](https://github.com/user-attachments/assets/70cfc394-df8d-4d9b-9c48-7306fe38d6fd)


<br><br><h2>Real-Time Messaging</h2>
I implemented WebSockets, which are responsible for sending messages in real-time.
![webstorm64_rq8hfpT7Ih](https://github.com/user-attachments/assets/e2142e01-05f5-4a9f-b7f1-a24527ee048c)




<br><br><h2>User Availability</h2>
There is also posibility to check whether user is online or offline.
Very handy here became the 'SessionConnectedEvent,' which I used in event listeners to check whether a user has connected to or disconnected from StompJS.
![msedge_491GLTYdwN](https://github.com/user-attachments/assets/704f98c4-51ac-4f0f-83e4-a422708e4ba3)



<br><br><br>

<h1>Backend</h1>
<h2>In this section, I will briefly discuss few tools/modules in my code.</h2>




<h3><b>AuthService</b></h3> I used this module to integrate Spring Security, JWT, and route other service ports through the API Gateway.Api Gateway fetches ports through the eureka. The entire implementation is written reactively.


<h3><b>RabbitMq</b></h1>Modules are communicating with each other via rabbitmq.I used mainly rabbit just for delivering userData. For example PostService communicates with userService when he
needs data about the user.


<h3> Redis </h3> I used the tool to dynamically manage user sessions. Once a user logs in, his username and session id is saved as a hash map in Redis. When the user leave the application, his session is removed.



<br><br><h1>Frontend</h1>
The frontend is made with Angular. I didn’t focus much on it. I just wanted to try frontend tools. 
There are still many things to fix, but the app works fine.

Below is a link to frontend repository<br>
https://github.com/kamiloses/FriendHub-Frontend



<br><br><br>
<h3>libraries/frameworks used :</h3>

- Java 17
- Spring boot 3
- Project Reactor
- Reactive MongoDb
- RabbitMq
- Eureka Discovery Server & client
- Angular
- Reactive Api gateway
- Spring security
- JWT
- Lombok
- Redis
- Junit
- Mockito


<br><br><br><br><br><br><br><br>
Fun Fact: While developing this app, I noticed for the first time how important a good computer is for programming because my computer barely handled so many backend modules running with Docker and the frontend at the same time.

