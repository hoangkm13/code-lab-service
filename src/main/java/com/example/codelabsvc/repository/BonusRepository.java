package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.Bonus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonusRepository extends MongoRepository<Bonus, String> {
}
