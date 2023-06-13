package com.example.codelabsvc.controller.request.challenge;

import com.example.codelabsvc.constant.Difficulty;
import com.example.codelabsvc.constant.Skill;
import com.example.codelabsvc.constant.Status;
import com.example.codelabsvc.constant.Subdomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChallengeDTO {
    private String id;

    @NotNull
    private String name;

    private Status status;

    @NotNull
    private Skill skill;

    @NotNull
    private Difficulty difficulty;

    @NotNull
    private Subdomain subDomain;

    @NotNull
    private Integer star;

    @NotNull
    private Integer points;

    private boolean isBookmark;

    @NotNull
    private String issue;

    private String testCaseId;

    @NotNull
    private List<String> bonusIds;
}
