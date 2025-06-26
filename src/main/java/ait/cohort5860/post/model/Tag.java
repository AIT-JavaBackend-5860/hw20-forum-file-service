package ait.cohort5860.post.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of ="name")
@Entity
@Table(name="tags")
public class Tag {
    @Id
    private String name;


}
