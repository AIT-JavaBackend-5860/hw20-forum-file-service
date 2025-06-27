package ait.cohort5860.post.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "posts")
public class Post {
    /*
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime dateCreated;
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Setter
    private String title;
    @Setter
    private String content;
    @Setter
    private String author;
    private LocalDateTime dateCreated = LocalDateTime.now();
    @Setter
    @ManyToMany
    private Set<Tag> tags = new HashSet<>();
    @Setter
    private int likes;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Post(String title, String content, String author, Set<String> tags) {
        this.title = title;
        this.content = content;
        this.author = author;
        // this.tags.addAll((tags.stream().map(tag -> new Tag(tag)).toList()));
        this.tags.addAll(tags.stream().map(Tag::new).toList());
    }

    public void addComment(Comment comment) {

        comments.add(comment);
        comment.setPost(this);     // INSERT INTO comments (message, username, post_id)
    }

    public void addLikes() {
        likes++;
    }

    public boolean addTag(Tag tag) {
        return tags.add(tag);
    }
}
