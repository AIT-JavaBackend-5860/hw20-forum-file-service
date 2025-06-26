package ait.cohort5860.post.service;

import ait.cohort5860.post.dao.PostRepository;
import ait.cohort5860.post.dao.TagRepository;
import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import ait.cohort5860.post.model.Post;
import ait.cohort5860.post.model.Tag;

import ait.cohort5860.post.dto.exceptions.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public PostDto addNewPost(String author, NewPostDto newPostDto) {
        Post post = new Post(newPostDto.getTitle(), newPostDto.getContent(),author);

        // Handle tags
        Set<String > tags = newPostDto.getTags();
        if (tags != null && !tags.isEmpty()) {


            for (String tagName : tags) {
                Tag tag = tagRepository.findById(tagName).orElseGet(
                        ()-> tagRepository.save(new Tag(tagName))
                );
                post.addTag(tag);
            }

        }

        post=postRepository.save(post);
        return modelMapper.map(post,PostDto.class);
    }

//    public PostDto addNewPost(String author, NewPostDto newPostDto) {
//        Post post = new Post(newPostDto.getTitle(), newPostDto.getContent(), author);
//        Set<String> tags = newPostDto.getTags();
//        //Handle tags
//        if (tags != null) {
//            for (String tagName : tags) {
//                Tag tag = tagRepository.findById(tagName).orElseGet(
//                        () -> tagRepository.save(new Tag(tagName))
//                );
//            }
//        }
//        postRepository.save(post);
//        return modelMapper.map(post, PostDto.class); //make DTO-obj
//    }

    @Override
    public PostDto findPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        return modelMapper.map(post, PostDto.class);
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
