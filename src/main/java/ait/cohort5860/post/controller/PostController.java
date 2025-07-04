package ait.cohort5860.post.controller;

import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import ait.cohort5860.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


// @CrossOrigin(origins="https://localhost:3000", methods={}, allowedHeaders = "*", maxAge = 3600) // без Spring Security
@RestController
@RequiredArgsConstructor
@RequestMapping("/forum") // Base path for all endpoints
public class PostController {
    private final PostService postService;

    // POST
    // Add a new post
    @PostMapping("/post/{author}")
    @ResponseStatus(HttpStatus.CREATED) // Returns HTTP 201
    public PostDto addNewPost(@PathVariable String author, @RequestBody @Valid NewPostDto newPostDto) {
        return postService.addNewPost(author, newPostDto);
    }

    // GET
    // Find one post by ID
    @GetMapping("/post/{id}")
    public PostDto findPostById(@PathVariable Long id) {
        return postService.findPostById(id);
    }

    // PATCH
    // Add a like to a post
    @PatchMapping("/post/{id}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Returns 204 No Content
    public void addLike(@PathVariable Long id) {
        postService.addLike(id);
    }

    // PATCH
    // Update post (title, content, tags)
    @PatchMapping("/post/{id}")
    public PostDto updatePost(@PathVariable Long id, @RequestBody NewPostDto newPostDto) {
        return postService.updatePost(id, newPostDto);
    }

    // DELETE
    // Delete a post by ID
    @DeleteMapping("/post/{id}")
    public PostDto deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }

    // PATCH
    // Add a comment to a post
    @PatchMapping("/post/{id}/comment/{author}")
    public PostDto addComment(@PathVariable Long id, @PathVariable String author, @RequestBody @Valid NewCommentDto newCommentDto) {
        return postService.addComment(id, author, newCommentDto);
    }

    // GET
    // Get all posts by author
    @GetMapping("/posts/author/{author}")
    public Iterable<PostDto> findPostsByAuthor(@PathVariable String author) {
        return postService.findPostsByAuthor(author);
    }

    // GET
    // Find posts by tags
    @GetMapping("/posts/tags")
    public Iterable<PostDto> findPostsByTags(@RequestParam("values") List<String> tags) {
        return postService.findPostsByTags(tags);
    }

    // GET
    // Find posts by date range
    @GetMapping("/posts/period")
    public Iterable<PostDto> findPostsByPeriod(@RequestParam("dateFrom") @NotNull(message="Date 'from' required") LocalDate from, @RequestParam("dateTo") @NotNull(message="Date 'to' required") LocalDate to) {
        return postService.findPostsByPeriod(from, to);
    }
}