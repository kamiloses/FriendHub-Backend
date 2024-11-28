package com.kamiloses.messageservice.controller;

import com.kamiloses.messageservice.dto.MessageDto;
import com.kamiloses.messageservice.entity.MessageEntity;
import com.kamiloses.messageservice.service.MessageService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/message")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class MessageController {

private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


//    @GetMapping("/{id}")
//public Mono<List<MessageDto>> showMessagesRelatedWithPost(@PathVariable(name = "id") String postId){
//
//return messageService.showMessageRelatedWithChat(postId);
//
//}



@GetMapping("/{username}")
public Flux<MessageDto> showMessagesRelatedWithUser(@PathVariable(name = "username") String username){
        return messageService.showMessagesRelatedWithUser(username);
}




// nie działa jeszcze
@PostMapping()
    public void sendMessage(@RequestBody MessageDto messageDto) {
    MessageEntity messageEntity = new MessageEntity();
    messageEntity.setChatId(messageDto.getChatId());
    messageEntity.setSenderUsername(messageDto.getSender().getUsername());
    messageEntity.setRecipientUsername(messageDto.getRecipient().getUsername());
    messageEntity.setContent(messageDto.getContent());
    messageEntity.setTimestamp(new Date());


}
//todo zamień potem void i w serwis wrzuć

}
