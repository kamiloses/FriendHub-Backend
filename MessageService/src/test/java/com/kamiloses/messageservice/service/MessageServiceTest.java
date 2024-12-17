package com.kamiloses.messageservice.service;

import com.kamiloses.messageservice.rabbit.RabbitMessageProducer;
import com.kamiloses.messageservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class MessageServiceTest {

     @MockBean
    private RabbitMessageProducer rabbitMessageProducer;
     @Autowired
    private MessageRepository messageRepository;

     @Autowired
    private MessageService messageService;







}