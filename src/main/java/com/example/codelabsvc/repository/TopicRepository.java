package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends MongoRepository<Topic, String> {
    @Query("{'_id' : { $in : ?0 } }")
    List<Topic> findAllById(List<String> topicIds);

}
