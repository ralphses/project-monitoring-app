package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.model.Comment;
import com.clicks.project_monitoring.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment save(String comment, String userRef) {
        Comment newComment = Comment.builder()
                .reference(UUID.randomUUID().toString())
                .content(comment)
                .userReference(userRef)
                .createdAt(LocalDateTime.now())
                .build();

        return commentRepository.save(newComment);
    }
}
