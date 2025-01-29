Readme 29.01.2025

<h1>FriendHub</h1>
<h3>
A full-stack application developed in a reactive style with a microservices architecture.
</h3>



<br><br>
<h3>Important - The project is not finished yet. I am working to make this app resemble Twitter and after that, I will upload it. Right now, you can check the current status of my project, where the most important features have been implemented. Some of the screenshots and GIFs may be outdated, but it's mainly CSS.</h3>
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
You can write posts. At this moment there is implemented 'findAll' method on the backend side, and on the home page, all the posts will be displayed.
I have implemented functions to like and retweet posts, as well as to undo likes and retweets. You can also check how many likes and retweets a post has, and depending on your choice, the button will change.
![image](https://github.com/user-attachments/assets/180f0e44-ff92-4680-a4f6-9d0d55dbbef6)






<br><br><h2>Comments</h2>

Once you click on a post, Angular will send a request to the backend to fetch comments related with the post, and you will be redirected to the post details page, where you can view all the comments associated with that specific post.
<br>You can write comments and response to other comments.

![image](https://github.com/user-attachments/assets/4ce59cda-b116-482d-b9eb-ffdffe89a866)




<br><br> <h2>Friends</h2>
You can add to friend or remove depending on if user is currenty on your friendList.
![image](https://github.com/user-attachments/assets/4dd098d2-22bc-4e47-99c6-607d021a1d39)


<br><br> <h2>Prometheus</h2>
Implemented gauge metrics to monitor the number of users currently using FriendHub. It is visualised in grafana.
![image](https://github.com/user-attachments/assets/7dfe7447-51c7-41d2-ae25-59260253e2a6)





<br><br>
<br> 02.01.2025<br><h2>Real-Time Messaging</h2>
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
needs data about the user. Rabbit is blocking but i wrapped it in reactive code, just for good looking code.


<h3> Redis </h3> I used the tool to dynamically manage user sessions. Once a user logs in, his username and session id is saved as a hash map in Redis. When the user leave the application, his session is removed. I also used Redis to store hashtags. On the backend side, I implemented a Redis ZSet with (hashtagName, ID, TimeOfCreation). The hashtag will be automatically removed after 24 hours.


<h3>Spring Batch</h3> I used the tool in HashtagService, and it is responsible for removing outdated hashtags. Schedulers invoke the Spring Batch job every 5 minutes. Spring Batch iterates through all hashtags in the Redis, checks which scores are older than 24 hours, and deletes the specific values.


<br><br><h1>Frontend</h1>

Below is a link to frontend repository<br>
https://github.com/kamiloses/FriendHub-Frontend



<br><br><br>
<h3>libraries/frameworks currently used :</h3>

- Java 17
- Spring boot 3
- Project Reactor
- Reactive MongoDb
- Reactive Api gateway
- Reactive Redis & Redis (blocking)
- RabbitMq
- Eureka Discovery Server & Client
- Spring Security
- JWT
- Jakarta Validation API
- Websockets
- Prometheus & Actuator
- Grafana
- Spring Batch
- MySql (Spring Batch)
- Lombok
- JUnit 5
- Mockito & WireMock
- Angular
