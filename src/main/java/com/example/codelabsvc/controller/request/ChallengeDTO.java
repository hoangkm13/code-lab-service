package com.example.codelabsvc.controller.request;

import com.example.codelabsvc.constant.Difficulty;
import com.example.codelabsvc.constant.Skill;
import com.example.codelabsvc.constant.Status;
import com.example.codelabsvc.constant.Subdomain;
import com.example.codelabsvc.entity.Bonus;
import com.example.codelabsvc.entity.Challenge;
import com.example.codelabsvc.entity.TestCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeDTO {

    private String name;

    private Status status;

    private Skill skill;

    private Difficulty difficulty;

    private Subdomain subDomain;

    private Integer star;

    private Integer points;

    private String issue;

    private List<TestCase> testCases;

    private List<Bonus> bonuses;
    public Challenge toChallenge(Challenge challenge){
        challenge.setId(UUID.randomUUID().toString());
        challenge.setName(this.getName()) ;
        challenge.setIssue(this.getIssue());
        challenge.setSkill(this.getSkill());
        challenge.setPoints(this.getPoints());
        challenge.setStar(this.getStar());
        challenge.setDifficulty(this.getDifficulty());
        challenge.setSubDomain(this.getSubDomain());
        challenge.setStatus(this.getStatus());
        challenge.setTestCases(this.getTestCases());

        return challenge;
    }
}
