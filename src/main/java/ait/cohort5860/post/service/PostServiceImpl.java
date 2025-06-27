package ait.cohort5860.post.service;

import ait.cohort5860.post.dao.PostRepository;
import ait.cohort5860.post.dao.TagRepository;
import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import ait.cohort5860.post.dto.exceptions.PostNotFoundException;
import ait.cohort5860.post.model.Comment;
import ait.cohort5860.post.model.Post;
import ait.cohort5860.post.model.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public PostDto addNewPost(String author, NewPostDto newPostDto) {
        Post post = new Post(newPostDto.getTitle(), newPostDto.getContent(), author);

        Set<String> tags = newPostDto.getTags();
        if (tags != null && !tags.isEmpty()) {

            for (String tagName : tags) { // go each tag name
                Tag tag = tagRepository.findById(tagName) // try to find tag by name (used as ID)
                        .orElseGet( // if not found
                                () -> tagRepository.save(new Tag(tagName)) // create and save new tag
                        );
                post.addTag(tag); // add tag to post
            }
        }

        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class); // convert to view object
    }

    @Override
    public PostDto findPostById(Long id) {

        Post post = postRepository.findById(id) // find the post by ID
                .orElseThrow(PostNotFoundException::new); // throw 404 exception if not found

        return modelMapper.map(post, PostDto.class); // convert to view object
    }

    @Override
    public void addLike(Long id) {

        Post post = postRepository.findById(id) // find the post by ID
                .orElseThrow(PostNotFoundException::new); // throw 404 if not found

        post.setLikes(post.getLikes() + 1); // like count +1

        postRepository.save(post);
    }

    @Override
    public PostDto updatePost(Long id, NewPostDto newPostDto) {
        Post post = postRepository.findById(id) // find the post by ID
                .orElseThrow(PostNotFoundException::new); // throw 404 exception if not found

        if (newPostDto.getTitle() != null) {
            post.setTitle(newPostDto.getTitle());
        }

        if (newPostDto.getContent() != null) {
            post.setContent(newPostDto.getContent());
        }

        if (newPostDto.getTags() != null) { // check if tag list is not null
            post.setTags( // set tag set for the post
                    newPostDto
                            .getTags() // get list of tag names as strings
                            .stream() // convert to stream
                            .map(Tag::new) // create Tag object from each string
                            .collect(
                                    Collectors.toSet() // all tags into a Set<Tag>
                            )
            );
        }

        postRepository.save(post);

        return modelMapper.map(post, PostDto.class); // view
    }

    @Override
    public PostDto deletePost(Long id) {

        Post post = postRepository.findById(id) // find the post by ID
                .orElseThrow(PostNotFoundException::new); // throw 404 if not found
        postRepository.delete(post); // delete post with cascade: comments and tags

        return null;

    }

    @Override
    public PostDto addComment(Long id, String author, NewCommentDto newCommentDto) {

        Post post = postRepository.findById(id) // find the post by ID
                .orElseThrow(PostNotFoundException::new); // throw 404 exception if not found

        // create new comment with author and message
        Comment comment = new Comment();
        comment.setUsername(author);
        comment.setMessage(newCommentDto.getMessage());

        comment.setPost(post); // link comment to post
        post.addComment(comment); // add comment to posts list

        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class); // convert to view object
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
