package ait.cohort5860.post.dao;

import ait.cohort5860.post.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
