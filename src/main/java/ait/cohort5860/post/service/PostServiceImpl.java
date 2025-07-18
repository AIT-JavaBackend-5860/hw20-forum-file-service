package ait.cohort5860.post.service;

import ait.cohort5860.post.dao.CommentRepository;
import ait.cohort5860.post.dao.PostRepository;
import ait.cohort5860.post.dao.TagRepository;
import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import ait.cohort5860.post.exception.PostNotFoundException;
import ait.cohort5860.post.model.Comment;
import ait.cohort5860.post.model.Post;
import ait.cohort5860.post.model.Tag;
import ait.cohort5860.post.service.logging.PostLogger;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
// @Slf4j(topic="Post Service") - log lombok annotation
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;
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
        // log.info("Finding post by id {}", id); - log to console
        Post post = postRepository.findById(id) // find the post by ID
                .orElseThrow(PostNotFoundException::new); // throw 404 exception if not found

        return modelMapper.map(post, PostDto.class); // convert to view object
    }

    @Override
    @Transactional
    @PostLogger
    public void addLike(Long id) {

        Post post = postRepository.findById(id) // find the post by ID
                .orElseThrow(PostNotFoundException::new); // throw 404 if not found

        post.setLikes(post.getLikes() + 1); // like count +1

        postRepository.save(post);
    }

    @Override
    @Transactional
    @PostLogger
    public PostDto updatePost(Long id, NewPostDto newPostDto) {
        // ищем пост по ID, если нет — выбрасываем ошибку
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        // если передан новый заголовок — обновляем
        if (newPostDto.getTitle() != null) {
            post.setTitle(newPostDto.getTitle());
        }

        // если передано новое содержание — обновляем
        if (newPostDto.getContent() != null) {
            post.setContent(newPostDto.getContent());
        }

        // если передан список тегов
        if (newPostDto.getTags() != null) {
            Set<Tag> tagSet = newPostDto.getTags().stream()
                    .map(tagName -> {
                        // пробуем найти тег в базе
                        return tagRepository.findById(tagName)
                                // если не найден — создаём и сохраняем
                                .orElseGet(() -> tagRepository.save(new Tag(tagName)));
                    })
                    .collect(Collectors.toSet());

            // обновляем теги у поста
            post.setTags(tagSet);
        }

        // сохраняем обновлённый пост
        postRepository.save(post);

        // возвращаем пост в виде DTO
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto deletePost(Long id) {

        Post post = postRepository.findById(id) // find the post by ID
                .orElseThrow(PostNotFoundException::new); // throw 404 if not found
        postRepository.delete(post); // delete post with cascade: comments and tags

        return null;

    }

    @Override
    @Transactional
    public PostDto addComment(Long id, String author, NewCommentDto newCommentDto) {

        Post post = postRepository.findById(id) // find the post by ID
                .orElseThrow(PostNotFoundException::new); // throw 404 exception if not found

        // create new comment with author and message
        Comment comment = new Comment(author, newCommentDto.getMessage());



        comment.setPost(post);                  // link comment to post
        commentRepository.save(comment);        // save comment only

        return modelMapper.map(post, PostDto.class); // convert to view object
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<PostDto> findPostsByAuthor(String author) {

        return postRepository.findByAuthorIgnoreCase(author).map(
                p -> modelMapper.map(p, PostDto.class))
                .toList();


    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<PostDto> findPostsByTags(List<String> tags) {

        return postRepository.findDistinctByTagsNameInIgnoreCase(tags).map(
                        p -> modelMapper.map(p, PostDto.class))
                .toList();

    }

    @Override
    @Transactional
    public Iterable<PostDto> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo) {
        LocalDateTime from =dateFrom.atStartOfDay();
        LocalDateTime to  =dateTo.atTime(LocalTime.MAX);

        return postRepository.findByDateCreatedBetween(from,to).map(
                        p -> modelMapper.map(p, PostDto.class))
                .toList();

    }
}
