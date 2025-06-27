package ait.cohort5860.post.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

/*
    private String user;
    private String message;
    private LocalDateTime dateCreated;
    private int likes;
 */

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of ="id")
@Entity
@Table(name="comments")
public class Comment {
    @Id // primary key for table 'comments'
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment (PostgreSQL style)
    private long id;
    @Setter
    private String username;
    @Setter
    private String message;
    private LocalDateTime dateCreated =  LocalDateTime.now();
    private int likes;
    @Setter
    @ManyToOne
    @JoinColumn(name = "post_id")  // fk to Post
    private Post post;

    public Comment(String message, String user) {
        this.message = message;
        this.username = user;
        this.dateCreated = LocalDateTime.now();
    }

    public void addLikes() {
        likes++;
    }
}
