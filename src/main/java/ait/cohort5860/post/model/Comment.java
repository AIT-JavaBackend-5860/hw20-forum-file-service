package ait.cohort5860.post.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String message;
    private LocalDateTime dateCreated =  LocalDateTime.now();
    private int likes;

    @ManyToOne
    private Post post;

    public Comment(String message, String user) {
        this.message = message;
        this.username = user;
    }

    public void addLikes() {
        likes++;
    }
}
