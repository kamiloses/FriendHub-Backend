package com.kamiloses.messageservice.controller;

import com.kamiloses.messageservice.dto.MessageDto;
import com.kamiloses.messageservice.dto.SendMessageDto;
import com.kamiloses.messageservice.service.MessageService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class MessageController {
    //todo sprawdź potem dlaczego kod nie wywoływał sie w przypadku routera


    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{chatId}")
    public Mono<List<MessageDto>> showMessagesRelatedWithChat(@PathVariable String chatId) {
        return messageService.showMessageRelatedWithChat(chatId);
    }


    @PostMapping()
    public Mono<Void> sendMessage(@RequestBody SendMessageDto messageDto) {
        return messageService.sendMessage(messageDto);


    }

}
