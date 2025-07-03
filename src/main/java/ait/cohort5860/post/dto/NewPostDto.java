package ait.cohort5860.post.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewPostDto {
    /*
    {
      "title": "JavaEE",
      "content": "Java is the best for backend",
      "tags": ["Java", "Spring", "backend", "JEE"]
    }
    */
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 10, max = 10000, message = "Content must be between 10 and 10000 characters")
    private String content;

    private Set<@Size(min = 1, max = 30, message = "Each tag must be 1-30 characters") String> tags;
}

