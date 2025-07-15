package ait.cohort5860.post.dao;

import ait.cohort5860.post.model.PostFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostFileRepository extends JpaRepository<PostFileEntity, Long> {

    List<PostFileEntity> findFilesByPost_Id(Long postId); // get all files by PostId
    void deletePostFileById(Long id);

}