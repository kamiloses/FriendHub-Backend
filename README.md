


 #  <h1 align="center">💬 FriendHub 💬</h1>




<br><br><br><br><h1>Frontend</h1>

<h3>Below is a link to the frontend repository where I explain how the application works.</h3><br>
<h3> https://github.com/kamiloses/FriendHub-Frontend</h3>



---
<br><br>
## 🚀About the Project
<h3>It's my main project which allows users to communicate with each other by writing posts and comments, giving likes and retweets, and sending real-time private messages.
The application is similar to Facebook or Twitter. It is written reactively using WebFlux and RabbitMQ (sync).<h3>
 







<br><br><br><br>
<h2>🗂️ Model of Database Connections</h2>
<p>Here is a model showing the connections between the databases:</p>

![image](https://github.com/user-attachments/assets/52327a6e-4808-4de9-b5fb-c8774e0986ab)


<br><br><br><br><br>
<h1>📚 API Documentation – Swagger Endpoints</h1>



<h2><strong>Comment Module</strong></h2>
<p>Responsible for displaying comments related to a specific post.</p>
<img src="https://github.com/user-attachments/assets/f1a1cc4e-1b4f-48e4-a8dc-51e256eabd03" alt="Comment Module" />

<h2><strong>Message Module</strong></h2>
<p>Responsible for displaying messages sent and received from friends in private conversations.</p>
<img src="https://github.com/user-attachments/assets/dde983f2-9374-45a2-a64f-6a4b26a221d5" alt="Message Module" />

<h2><strong>Post & Retweet Module</strong></h2>
<p>Responsible for writing posts, displaying posts, and retweeting posts.</p>
<img src="https://github.com/user-attachments/assets/91c46ecf-777c-4400-9bc1-ce09c4a428e9" alt="Post & Retweet Module" />

<h2><strong>User Service</strong></h2>
<p>Responsible for registration, sending data about the logged-in user, and checking whether the user already exists in the database during registration.</p>
<img src="https://github.com/user-attachments/assets/e9a4927f-1ac8-4038-a850-ba395677014a" alt="User Service" />

<h2><strong>Likes Module</strong></h2>
<p>Responsible for showing likes and allowing users to like posts.</p>
<img src="https://github.com/user-attachments/assets/43f0917c-b124-429d-83af-faa4a6586be6" alt="Likes Module" />

<h2><strong>Friend Module</strong></h2>
<p>Responsible for adding/removing users to/from the friend list and checking whether a user is already a friend.</p>
<img src="https://github.com/user-attachments/assets/e3590e8d-d64d-48a2-a404-7e394fb499a3" alt="Friend Module" />

<h2><strong>Auth Module</strong></h2>
<p>All modules are connected to this one. It is responsible for securing endpoints and delivering JWTs upon successful login.</p>
<img src="https://github.com/user-attachments/assets/5de16a20-9d1f-4f49-a1ce-399570d7125c" alt="Auth Module" />



<br><br><br><br>
<h1>🔍In this section, I will briefly discuss few tools/modules in my code.</h1>




<h3><b>AuthService</b></h3> I used this module to integrate Spring Security, JWT, and route other service ports through the API Gateway.Api Gateway fetches ports through the eureka. The entire implementation is written reactively.


<h3><b>RabbitMq</b></h1>Modules are communicating with each other via rabbitmq.I used mainly rabbit just for delivering userData. For example PostService communicates with userService when he
needs data about the user. Rabbit is blocking but i wrapped it in reactive code, just for good looking code.


<h3> Redis </h3> I used the tool to dynamically manage user sessions. Once a user logs in, his username and session id is saved as a hash map in Redis. When the user leave the application, his session is removed. I also used Redis to store hashtags. On the backend side, I implemented a Redis ZSet with (hashtagName, ID, TimeOfCreation). The hashtag will be automatically removed after 24 hours.


<h3>Spring Batch</h3> I used the tool in HashtagService, and it is responsible for removing outdated hashtags. Schedulers invoke the Spring Batch job every 5 minutes. Spring Batch iterates through all hashtags in the Redis, checks which scores are older than 24 hours, and deletes the specific values.


<br><br> <h3>Prometheus</h3>
Implemented gauge metrics to monitor the number of users currently using FriendHub. It is visualised in grafana.
![image](https://github.com/user-attachments/assets/7dfe7447-51c7-41d2-ae25-59260253e2a6)



<br><br><br>
<h2>📚libraries/frameworks used :</h2>

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

<br><br>
<h1> ## 🛠️ Run the Application  </h1>

1. Open the link and clone the repository:  
   🔗 [FriendHub-compose](https://github.com/kamiloses/FriendHub-compose)  

2. Run the following command:  

   ```sh
   docker compose up --build

3. - The application runs on localhost:4200.  
   - The Compose file contains the backend along with the frontend.



