package ait.cohort5860.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostFileResponseDto {
    private Long id;
    private String fileName;
    private String contentType;
    private long size;
}