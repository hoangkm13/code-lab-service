package com.example.codelabsvc.repository.impl;

import com.example.codelabsvc.entity.Chat;
import com.example.codelabsvc.repository.ChatCustomRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ChatRepositoryImpl implements ChatCustomRepository {
    private final MongoTemplate mongoTemplate;

    public ChatRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Integer> getAllChatIds(String sender) {
        Query query = new Query();
        query.fields().include("chatId");
        query.addCriteria(Criteria.where("sender").is(sender));
        List<Chat> chats = mongoTemplate.find(query, Chat.class);
        return chats.stream().map(Chat::getChatId).collect(Collectors.toList()).stream().sorted().distinct().collect(Collectors.toList());
    }
}
