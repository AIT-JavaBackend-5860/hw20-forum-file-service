package ait.cohort5860.post.service;

import ait.cohort5860.post.dto.PostFileDto;
import ait.cohort5860.post.model.PostFileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostFileService {
    PostFileDto uploadFileToPost(Long postId, MultipartFile file);  // Загрузить файл к посту
    PostFileEntity getFileById(Long fileId);                                // Получить файл по ID (для скачивания)
    void deletePostFileById(Long fileId);                                   // Удалить файл
    List<PostFileDto> getFileMetasByPostId(Long postId);               // Получить метаданные файлов поста
}
