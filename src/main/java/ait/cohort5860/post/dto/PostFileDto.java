package ait.cohort5860.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostFileDto {
    private Long id;

    @JsonProperty("filename") // если хочешь имя как в JSON
    private String fileName;

    private String contentType;
    private long size;
}