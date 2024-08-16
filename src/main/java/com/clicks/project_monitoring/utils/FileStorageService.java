package com.clicks.project_monitoring.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    public static final String UPLOAD_LOCATION = "src/main/resources/uploads/projects";
    private final Path fileStorageLocation;

    public FileStorageService() {
        // Store files in the resources/uploads directory
        this.fileStorageLocation = Paths.get(UPLOAD_LOCATION).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String upload(String projectReference, MultipartFile file) {
        // Construct the file name using the student's projectReference number and original file name
        String fileName = projectReference + "_" + file.getOriginalFilename();

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String updateFile(String existingFilePath, MultipartFile newFile) {
        Path targetLocation = Paths.get(existingFilePath);

        try {
            // Replace the existing file with the new file
            Files.copy(newFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return targetLocation.toString();
        } catch (IOException ex) {
            throw new RuntimeException("Could not update file " + existingFilePath + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String projectReference) {
        try {
            Path filePath = this.fileStorageLocation.resolve(projectReference).normalize();
            System.out.println("filePath = " + filePath);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + projectReference);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + projectReference, ex);
        }
    }
}
