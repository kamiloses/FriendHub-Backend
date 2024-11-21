package com.kamiloses.messageservice.service;

import com.kamiloses.messageservice.dto.MessageDto;
import com.kamiloses.messageservice.repository.MessageRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MessageService {

private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Mono<List<MessageDto>> MessageDtoShowMessageRelatedWithChat(String chatId){

  return   messageRepository.findByChatId(chatId).map(messageEntity -> {
    MessageDto messageDto = new MessageDto();
    messageDto.setContent();
    messageDto.setRecipientID();
    })}

}








}
