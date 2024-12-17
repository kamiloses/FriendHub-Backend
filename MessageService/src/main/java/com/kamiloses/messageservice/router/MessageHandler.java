//package com.kamiloses.messageservice.router;
//
//import com.kamiloses.messageservice.service.MessageService;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.BodyInserters;
//
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class MessageHandler {
//
//
//
//private final MessageService messageService;
//
//    public MessageHandler(MessageService messageService) {
//        this.messageService = messageService;
//    }
//
//
//    public Mono<ServerResponse> showMessageRelatedWithChat(ServerRequest request) {
//        String chatId = request.pathVariable("chatId");
//
//        return messageService.showMessageRelatedWithChat(chatId)
//                .map(messages ->
//                        messages.stream()
//                                .map(message ->
//                                        ServerResponse.ok()
//                                                .contentType(MediaType.APPLICATION_JSON)
//                                                .body(BodyInserters.fromValue(message))
//                                )
//                                .collect(Collectors.toList()));
//
//
//
//
//    }
//}