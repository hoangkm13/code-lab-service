package com.example.codelabsvc.entity;

import com.example.codelabsvc.constant.Difficulty;
import com.example.codelabsvc.constant.Skill;
import com.example.codelabsvc.constant.Status;
import com.example.codelabsvc.constant.Subdomain;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "challenge")

public class Challenge extends EntityBase {
    @Id
    private String id;

    private String name;

    private Status status;

    private Skill skill;

    private Difficulty difficulty;

    private Subdomain subDomain;

    private Integer star;

    private Integer points;

    private String preScript;

    private String script;

    private String issue;

    private List<TestCase> testCases;

    private List<Bonus> bonuses;
}
