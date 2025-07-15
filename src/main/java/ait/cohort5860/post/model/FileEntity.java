package ait.cohort5860.post.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity // class as table
@NoArgsConstructor // empty constructor
@AllArgsConstructor // constructor with all fields
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    //@Column(name = "file_name")
    private String fileName;
    @Setter
    //@Column(name = "content_type")
    private String contentType;

    @Lob
    @Setter
    //@Column(nullable = false)
    private byte[] data;
}
