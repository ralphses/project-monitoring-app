package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.response.AllProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProjectService {
    public AllProjectResponse getAllProjects(Integer page) {
        return null;
    }

    public AllProjectResponse getAllSupervisorProjects(Integer page) {
        return null;
    }

    public String upload(String studentMatric, MultipartFile file) {
        return null;
    }

    public AllProjectResponse getProjectFile(String project) {
        return null;
    }
}
