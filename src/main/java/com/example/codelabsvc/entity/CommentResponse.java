package com.example.codelabsvc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "comment_response")
public class CommentResponse extends EntityBase {
    @Id
    private String id;
    private String commentId;
    private User user;
    private String text;

    public CommentResponse(String commentId,User user, String text) {
        super(new Date().toString(), "", user.getUsername(), user.getUsername());
        this.commentId = commentId;
        this.user = user;
        this.text = text;
    }
}
