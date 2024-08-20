package com.clicks.project_monitoring.repositories;

import com.clicks.project_monitoring.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByTaskReference(String taskReference, Pageable pageable);
    Comment findTopByTaskReferenceOrderByCreatedAtDesc(String taskReference);

}
