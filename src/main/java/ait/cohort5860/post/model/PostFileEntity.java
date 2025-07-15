package ait.cohort5860.post.model;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Table(name = "post_file")
public class PostFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String contentType;

    private long size; // add file size meta info

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
