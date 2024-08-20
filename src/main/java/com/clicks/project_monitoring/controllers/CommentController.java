package com.clicks.project_monitoring.controllers;

import com.clicks.project_monitoring.dtos.requests.comments.NewCommentRequest;
import com.clicks.project_monitoring.dtos.response.AllCommentsResponse;
import com.clicks.project_monitoring.service.CommentService;
import com.clicks.project_monitoring.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public CustomResponse getAllComments(
            @RequestParam("taskReference") String taskReference,
            @RequestParam(value = "page", defaultValue = "1") Integer page
    ) {
        AllCommentsResponse response = commentService.getAllComments(taskReference, page);
        return new CustomResponse(true, response);
    }

    @PostMapping
    public CustomResponse addComment(@RequestBody NewCommentRequest request) {
        System.out.println(request);
        String response = commentService.save(request);
        return new CustomResponse(true, response);
    }

}
