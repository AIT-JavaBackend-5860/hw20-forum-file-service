package ait.cohort5860.post.dto;

import lombok.Data;

@Data
public class FileResponseDto {
    private Long id;
    private String filename;
    private String downloadUrl;
    private String contentType;
    private long size;
}
