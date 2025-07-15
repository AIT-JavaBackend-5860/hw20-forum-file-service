package ait.cohort5860.post.service;

import ait.cohort5860.post.dao.PostFileRepository;
import ait.cohort5860.post.dao.PostRepository;
import ait.cohort5860.post.dto.PostFileDto;
import ait.cohort5860.post.exception.PostFileNotFoundException;
import ait.cohort5860.post.exception.PostNotFoundException;
import ait.cohort5860.post.model.Post;
import ait.cohort5860.post.model.PostFileEntity;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostFileServiceImpl implements PostFileService {

    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostFileDto uploadFileToPost(Long postId, MultipartFile file) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Пост с id " + postId + " не найден"));
        try {
            PostFileEntity fileEntity = new PostFileEntity();
            fileEntity.setPost(post);
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setContentType(file.getContentType());
            fileEntity.setData(file.getBytes());
            fileEntity.setSize(file.getSize());

            PostFileEntity saved = postFileRepository.save(fileEntity);
            return modelMapper.map(saved, PostFileDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении файла: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PostFileEntity getFileById(Long fileId) {
        return postFileRepository.findById(fileId)
                .orElseThrow(() -> new PostFileNotFoundException("Файл с id " + fileId + " не найден"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostFileDto> getFileMetasByPostId(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Пост с id " + postId + " не найден"));

        List<PostFileEntity> files = postFileRepository.findFilesByPost_Id(postId);

        return files.stream()
                .map(file -> modelMapper.map(file, PostFileDto.class))
                .toList();
    }

    @Override
    public void deletePostFileById(Long fileId) {
        PostFileEntity file = postFileRepository.findById(fileId)
                .orElseThrow(() -> new PostFileNotFoundException("Файл с id " + fileId + " не найден"));
        postFileRepository.delete(file);
    }
}
