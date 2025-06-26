package ait.cohort5860.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    /*
    {
      "user": "Stranger",
      "message": "Awesome!!!",
      "dateCreated": "2021-12-15T14:11:55",
      "likes": 0
    }
    */
    private String user;
    private String message;
    private LocalDateTime dateCreated;
    private int likes;
}

