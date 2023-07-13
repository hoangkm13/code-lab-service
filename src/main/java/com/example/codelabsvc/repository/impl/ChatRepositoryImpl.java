package com.example.codelabsvc.repository.impl;

import com.example.codelabsvc.entity.Chat;
import com.example.codelabsvc.entity.UserTopic;
import com.example.codelabsvc.repository.ChatCustomRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class ChatRepositoryImpl implements ChatCustomRepository {
    private final MongoTemplate mongoTemplate;

    public ChatRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Chat> getAllChatIds(String sender) {

        Sort sort = Sort.by(Sort.Order.desc("createdAt"));

        Criteria criteria = new Criteria().orOperator(
                Criteria.where("sender").is(sender),
                Criteria.where("receiver").is(sender));

        Query query = new Query();
        query.addCriteria(criteria).with(sort);

        List<Chat> chats = mongoTemplate.find(query, Chat.class);

        return chats.stream().filter(chat -> chat.getReceiver() != null)
                .filter(distinctByKey(Chat::getChatId)).collect(Collectors.toList());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
