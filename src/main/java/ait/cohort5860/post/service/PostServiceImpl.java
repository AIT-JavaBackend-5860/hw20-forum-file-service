package ait.cohort5860.post.service;

import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    @Override
    public PostDto addNewPost(String author, NewPostDto newPostDto) {
        return null;
    }

    @Override
    public PostDto findPostById(Long id) {
        return null;
    }

    @Override
    public void addLike(Long id) {

    }

    @Override
    public PostDto updatePost(Long id, NewPostDto newPostDto) {
        return null;
    }

    @Override
    public PostDto deletePost(Long id) {
        return null;
    }

    @Override
    public PostDto addComment(Long id, String author, NewCommentDto newCommentDto) {
        return null;
    }

    @Override
    public Iterable<PostDto> findPostsByAuthor(String author) {
        return null;
    }

    @Override
    public Iterable<PostDto> findPostsByTags(List<String> tags) {
        return null;
    }

    @Override
    public Iterable<PostDto> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo) {
        return null;
    }
}
