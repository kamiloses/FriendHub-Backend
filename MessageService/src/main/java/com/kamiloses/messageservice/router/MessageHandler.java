package com.kamiloses.messageservice.router;

import com.kamiloses.messageservice.dto.SendMessageDto;
import com.kamiloses.messageservice.service.MessageService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
@Component
public class MessageHandler {




    private final MessageService messageService;

    public MessageHandler(MessageService messageService) {
        this.messageService = messageService;
    }


    public Mono<ServerResponse> showMessagesRelatedWithChat(ServerRequest request) {
        return messageService.showMessageRelatedWithChat(request.pathVariable("chatId"))
                .flatMap(messages -> ServerResponse.ok().bodyValue(messages));

    }

    public Mono<ServerResponse> sendMessage(ServerRequest request) {

        return request.bodyToMono(SendMessageDto.class).flatMap(messageService::sendMessage).then(ServerResponse.ok().build());

    }





}
