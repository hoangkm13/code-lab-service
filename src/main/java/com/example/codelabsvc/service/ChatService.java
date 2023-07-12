package com.example.codelabsvc.service;

import com.example.codelabsvc.entity.Chat;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ChatService {
    Flux<Chat> getMessages(Integer chatId);

    Mono<Chat> newMessage(Chat chat);

    Flux<Chat> findChatByUser(String user);

    List<Chat> getAllChatIds(String sender);

}
