package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.entity.Chat;
import com.example.codelabsvc.entity.User;
import com.example.codelabsvc.repository.ChatRepository;
import com.example.codelabsvc.service.ChatService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Flux<Chat> getMessages(Integer chatId) {
        return chatRepository.findByChatId(chatId)
                .subscribeOn(Schedulers.boundedElastic());
    }


    public Mono<Chat> newMessage(Chat chat) {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        chat.setSender(authentication.getUsername());
        chat.setCreatedAt(LocalDateTime.now());
        return chatRepository.save(chat);
    }

    @Override
    public Flux<Chat> findChatByUser(String user) {
        return chatRepository.findChatByUser(user).subscribeOn(Schedulers.boundedElastic());
    }


}
