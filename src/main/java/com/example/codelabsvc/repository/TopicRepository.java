package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.TestCase;
import com.example.codelabsvc.entity.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TopicRepository extends MongoRepository<Topic, String> {

}
