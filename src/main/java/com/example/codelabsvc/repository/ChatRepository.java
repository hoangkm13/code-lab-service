package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.Chat;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {
    @Tailable
    Flux<Chat> findByChatId(Integer chatId);
    @Tailable
    @Query( "[{'sender': ?0},{'receiver': ?0}]")
    Flux<Chat> findChatByUser(String user);

    @Tailable
    @Query( "[{'sender': ?0},{'receiver': ?0}]")
    Flux<Chat> Fin(String user);
}
