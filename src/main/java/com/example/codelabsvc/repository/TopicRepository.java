package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TopicRepository extends MongoRepository<Topic, String> {
}
