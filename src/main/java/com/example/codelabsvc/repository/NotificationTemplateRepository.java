package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.NotificationTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTemplateRepository extends MongoRepository<NotificationTemplate, String> {
}
