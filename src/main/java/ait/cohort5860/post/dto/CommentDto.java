package ait.cohort5860.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("user")
    private String username;
    private String message;
    private LocalDateTime dateCreated;
    private int likes;
}

