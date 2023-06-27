package com.example.codelabsvc.entity;

import com.example.codelabsvc.constant.Difficulty;
import com.example.codelabsvc.constant.Skill;
import com.example.codelabsvc.constant.Status;
import com.example.codelabsvc.constant.Subdomain;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "challenge")

public class Challenge extends EntityBase {

    private String id;

    private String name;

    private Skill skill;

    private Difficulty difficulty;

    private Subdomain subDomain;

    private Integer points;

    private String issue;

    private List<String> bonusIds;

    public String getFieldValue(String fieldName) {
        switch (fieldName) {
            case "skill":
                return skill.value();
            case "difficulty":
                return difficulty.value();
            case "subDomain":
                return subDomain.value();
            default:
                throw new IllegalArgumentException("Unknown field name: " + fieldName);
        }
    }

}
