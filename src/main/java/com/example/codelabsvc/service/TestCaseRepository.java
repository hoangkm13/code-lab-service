package com.example.codelabsvc.service;

import com.example.codelabsvc.entity.TestCase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestCaseRepository extends MongoRepository<TestCase, String> {

}
