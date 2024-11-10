package com.kamiloses.postservice.rabbitMq;

import com.kamiloses.postservice.service.PostService;
import com.kamiloses.rabbitmq.RabbitConfig;
import com.kamiloses.userservice.dto.UserDetailsDto;
import com.kamiloses.userservice.rabbit.RabbitUserListener;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class RabbitPostSenderTest {

    @InjectMocks
    private PostService postService;



    @Container
    private static RabbitMQContainer rabbitMQContainer =
            new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.7.25-management-alpine"));

    private static RabbitTemplate rabbitTemplate;

    @BeforeAll
    static void setup() {
        rabbitMQContainer.start();

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
                rabbitMQContainer.getHost(), rabbitMQContainer.getAmqpPort());
        rabbitTemplate = new RabbitTemplate(connectionFactory);

    }

    @AfterAll
    static void cleanup() {
        rabbitMQContainer.stop();
    }

    @Test
    void test_convert_Send_And_Receive_Communication() {
        doReturn(UserDetailsDto.builder().firstName("test").build()).when(rabbitTemplate.receive());

        String response = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.Exchange_To_User_Service, RabbitConfig.ROUTING_KEY_, "x");
//todo popraw test
        System.err.println(response);
        assertNotNull(response);
    }


}
