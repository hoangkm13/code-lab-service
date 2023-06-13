package com.example.codelabsvc.controller.request.challenge;

import com.example.codelabsvc.constant.Difficulty;
import com.example.codelabsvc.constant.Skill;
import com.example.codelabsvc.constant.Status;
import com.example.codelabsvc.constant.Subdomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChallengeDTO {
    private String challengeId;

    private String name;

    private Status status;

    private Skill skill;

    private Difficulty difficulty;

    private Subdomain subDomain;

    private Integer star;

    private Integer points;

    private boolean isBookmark;

    private String issue;

    private String testCaseId;

    private List<String> bonusIds;
}
