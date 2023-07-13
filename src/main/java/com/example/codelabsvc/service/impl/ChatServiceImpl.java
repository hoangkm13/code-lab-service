package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.entity.Chat;
import com.example.codelabsvc.entity.User;
import com.example.codelabsvc.repository.ChatCustomRepository;
import com.example.codelabsvc.repository.ChatRepository;
import com.example.codelabsvc.service.ChatService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    private final ChatCustomRepository chatCustomRepository;

    public ChatServiceImpl(ChatRepository chatRepository, ChatCustomRepository chatCustomRepository) {
        this.chatRepository = chatRepository;
        this.chatCustomRepository = chatCustomRepository;
    }

    public Flux<Chat> getMessages(Integer chatId) {
        return chatRepository.findByChatId(chatId)
                .subscribeOn(Schedulers.boundedElastic());
    }


    public Mono<Chat> newMessage(Chat chat) {
        chat.setCreatedAt(LocalDateTime.now());
        return chatRepository.save(chat);
    }

    @Override
    public Flux<Chat> findChatByUser(String user) {
        return chatRepository.findChatByUser(user).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public List<Chat> getAllChatIds(String sender) {

        return this.chatCustomRepository.getAllChatIds(sender);
    }


}
