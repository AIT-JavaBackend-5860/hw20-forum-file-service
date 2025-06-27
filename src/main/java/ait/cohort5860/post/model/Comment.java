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
    @Column(name = "id")
    private long id;
    @Setter
    @Column(name = "username")
    private String username;
    @Setter
    @Column(name = "message",columnDefinition = "TEXT")
    private String message;
    @Column(name = "date_created")
    private LocalDateTime dateCreated =  LocalDateTime.now();
    @Column(name = "likes")
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
