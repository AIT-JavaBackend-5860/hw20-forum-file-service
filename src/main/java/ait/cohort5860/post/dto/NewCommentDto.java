package ait.cohort5860.post.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewCommentDto {
    /*
    {
      "message": "Awesome!!!"
    }
    */
    @NotBlank(message = "Message is required")
    @Size(min = 1, max = 1000, message = "Message must be between 1 and 1000 characters")
    private String message;
}

