Readme 13.02.2025

# FriendHub  

### A full-stack application developed in a reactive style with a microservices architecture.  

---

## About the Project  

It's my main project, where I used most of the backend libraries I know.  
Instructions on how to run the application can be found at the very bottom.  

**Remember** – I was mainly focused on the backend. The frontend is only an addition, so there are still many things that need to be changed.  

<strong>26.05.2025</strong>
⚠️ Some of the screenshots and GIFs are outdated, but it's mainly CSS. I am currently learning Angular once again to fully change the frontend code.   
![image](https://github.com/user-attachments/assets/f1a1cc4e-1b4f-48e4-a8dc-51e256eabd03)

![image](https://github.com/user-attachments/assets/dde983f2-9374-45a2-a64f-6a4b26a221d5)

![image](https://github.com/user-attachments/assets/91c46ecf-777c-4400-9bc1-ce09c4a428e9)

![image](https://github.com/user-attachments/assets/e9a4927f-1ac8-4038-a850-ba395677014a)

![image](https://github.com/user-attachments/assets/43f0917c-b124-429d-83af-faa4a6586be6)

![image](https://github.com/user-attachments/assets/e3590e8d-d64d-48a2-a404-7e394fb499a3)

![image](https://github.com/user-attachments/assets/5de16a20-9d1f-4f49-a1ce-399570d7125c)




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







## Run the Application  

1. Open the link and clone the repository:  
   🔗 [FriendHub-compose](https://github.com/kamiloses/FriendHub-compose)  

2. Run the following command:  

   ```sh
   docker compose up --build

3. The application runs on localhost:4200.




