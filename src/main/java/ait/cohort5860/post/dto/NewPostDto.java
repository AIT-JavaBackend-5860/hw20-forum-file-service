package ait.cohort5860.post.dto;

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
    private String title;
    private String content;
    private Set<String> tags;
}

