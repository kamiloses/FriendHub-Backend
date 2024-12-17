package com.kamiloses.messageservice.service;

import com.kamiloses.messageservice.dto.MessageDto;
import com.kamiloses.messageservice.dto.SendMessageDto;
import com.kamiloses.messageservice.entity.MessageEntity;
import com.kamiloses.messageservice.exception.MessageDatabaseFetchException;
import com.kamiloses.messageservice.rabbit.RabbitMessageProducer;
import com.kamiloses.messageservice.repository.MessageRepository;
import com.kamiloses.rabbitmq.exception.RabbitExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j  @Import(RabbitExceptionHandler.class)
public class MessageService {

    private final MessageRepository messageRepository;
    private final RabbitMessageProducer rabbitMessageProducer;

    public MessageService(MessageRepository messageRepository, RabbitMessageProducer rabbitMessageProducer) {
        this.messageRepository = messageRepository;
        this.rabbitMessageProducer = rabbitMessageProducer;
    }

    public Mono<List<MessageDto>> showMessageRelatedWithChat(String chatId) {
        return messageRepository.findByChatId(chatId)
                .onErrorResume(error -> {
                    log.error("There was some problem with fetching messages from database");
                    return Mono.error(MessageDatabaseFetchException::new);})
                .flatMap(messageEntity ->
                        Mono.fromSupplier(() -> rabbitMessageProducer.askForUserDetails(messageEntity.getSenderUsername()))
                                .map(sender -> MessageDto.builder()
                                        .sender(sender)
                                        .content(messageEntity.getContent())
                                        .build())
                )
                .collectList();}






public Mono<Void> sendMessage(SendMessageDto messageDto) {


    return Mono.fromSupplier(() ->
                    MessageEntity.builder()
                            .chatId(messageDto.getChatId())
                            .senderUsername(messageDto.getSenderUsername())
                            .content(messageDto.getContent()).build())

            .flatMap(message -> messageRepository.save(message)
                    .onErrorResume(error -> {
                        log.error("There was some problem with saving message to database");
                        return Mono.error(MessageDatabaseFetchException::new);
                    })
                    .then());

}






}