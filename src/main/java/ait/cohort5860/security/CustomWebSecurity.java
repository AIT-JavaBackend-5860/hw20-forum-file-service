package ait.cohort5860.security;

import ait.cohort5860.post.dao.PostFileRepository;
import ait.cohort5860.post.dao.PostRepository;
import ait.cohort5860.post.model.Post;
import ait.cohort5860.post.model.PostFileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomWebSecurity {

    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;

    public boolean checkPostAuthor(String postId, String username){

        // Найти пост и проверить что в нем юзер нейм совпадает с автором
        try {
            Long id = Long.parseLong(postId);

            Post post = postRepository.findById(id).orElse(null);
            return post!= null && post.getAuthor().equalsIgnoreCase(username);
        } catch (NumberFormatException e) {
           return false;
        }

    }

    // Найти файл по id и проверить, что автор поста совпадает с именем пользователя
    public boolean checkFileAuthor(String fileId, String username) {
        try {
            Long id = Long.parseLong(fileId);

            PostFileEntity file = postFileRepository.findById(id).orElse(null);

            return file != null
                    && file.getPost() != null
                    && file.getPost().getAuthor().equalsIgnoreCase(username);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    }
