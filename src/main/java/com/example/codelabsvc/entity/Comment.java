package com.example.codelabsvc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "comment")

public class Comment extends EntityBase {

    private String id ;
    private String challengeId;
    private String userName;
    private String text;
    private String code;
    public Boolean isParent;
    public List<String> childCommentId;

    public Comment( String challengeId, String username, String text, String code,Boolean isParent,List<String> childCommentId) {
        super(LocalDateTime.now().toString(),"" ,username,username);
        this.challengeId = challengeId;
        this.userName = username;
        this.text = text;
        this.code = code;
        this.isParent = isParent;
        this.childCommentId = childCommentId;

    }

    private String commentText;
}
