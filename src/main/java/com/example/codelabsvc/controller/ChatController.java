package com.example.codelabsvc.controller;

import com.example.codelabsvc.entity.Chat;

import com.example.codelabsvc.service.ChatService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping(value = "{chatId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getMessages(@PathVariable Integer chatId) {
        return chatService.getMessages(chatId);
    }

    @PostMapping
    public Mono<Chat> newMessage(@RequestBody Chat chat) {
        chat.setCreatedAt(LocalDateTime.now());
        return chatService.newMessage(chat);
    }
    @GetMapping(value = "/test/{user}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> test(@PathVariable String user) {
        return chatService.findChatByUser(user);
    }
}
