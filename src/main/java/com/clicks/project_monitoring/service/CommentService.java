package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.requests.comments.NewCommentRequest;
import com.clicks.project_monitoring.dtos.response.AllCommentsResponse;
import com.clicks.project_monitoring.exceptions.ResourceNotFoundException;
import com.clicks.project_monitoring.model.Comment;
import com.clicks.project_monitoring.model.Task;
import com.clicks.project_monitoring.repositories.CommentRepository;
import com.clicks.project_monitoring.utils.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final TaskService taskService;
    private final DtoMapper mapper;

    @Transactional
    public String save(NewCommentRequest request) {

        Task task = taskService.findByReference(request.taskReference());
        String userName = userService.findUserName(request.userReference());

        Comment newComment = Comment.builder()
                .content(request.comment())
                .createdAt(LocalDateTime.now())
                .reference(UUID.randomUUID().toString())
                .taskReference(request.taskReference())
                .user(userName)
                .build();

        Comment savedComment = commentRepository.save(newComment);
        task.getComments().add(savedComment);

        return "Comment added successfully";
    }

    @Transactional
    public AllCommentsResponse getAllComments(String taskReference, Integer page) {
        if (taskService.existByReference(taskReference)) {
            Page<Comment> allByTaskPage = commentRepository
                    .findAllByTaskReference(taskReference, PageRequest.of(Math.max(0, (page - 1)), 10));
            return new AllCommentsResponse(
                    allByTaskPage.getTotalPages(),
                    allByTaskPage.getTotalElements(),
                    allByTaskPage.map(mapper::commentToCommentDto)
                            .toList());
        }
        throw new ResourceNotFoundException("Invalid task reference");
    }
}
