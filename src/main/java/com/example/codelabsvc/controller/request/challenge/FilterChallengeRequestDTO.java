package com.example.codelabsvc.controller.request.challenge;

import com.example.codelabsvc.entity.Challenge;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class FilterChallengeRequestDTO {
    private Integer page;
    private Integer size;
    private List<Challenge> challenges;
    private Map<String,List<String>> fieldValues;


}
