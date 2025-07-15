package ait.cohort5860.post.service;

import ait.cohort5860.post.dto.PostFileMetaDto;
import ait.cohort5860.post.dto.PostFileResponseDto;
import ait.cohort5860.post.model.PostFileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostFileService {
    PostFileResponseDto uploadFileToPost(Long postId, MultipartFile file);  // Загрузить файл к посту
    PostFileEntity getFileById(Long fileId);                                // Получить файл по ID (для скачивания)
    void deletePostFileById(Long fileId);                                   // Удалить файл
    List<PostFileMetaDto> getFileMetasByPostId(Long postId);               // Получить метаданные файлов поста
}
