package com.example.codelabsvc.controller.request.challenge;

import com.example.codelabsvc.entity.Challenge;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FilterChallengeRequest {
    private int page;
    private int size;
    private List<Challenge> challenges;
    private Map<String, List<String>> fieldValues;
}
