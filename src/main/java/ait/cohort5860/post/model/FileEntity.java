package ait.cohort5860.post.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // class as table
@Data //  геттеры, сеттеры, toString, equals, hashCode
@NoArgsConstructor // empty constructor
@AllArgsConstructor // constructor with all fields
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;
    @Column(name = "content_type")
    private String contentType;

    @Lob
    @Column(nullable = false)
    private byte[] data;
}
