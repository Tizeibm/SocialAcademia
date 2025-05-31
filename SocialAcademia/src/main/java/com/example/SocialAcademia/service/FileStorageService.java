package com.example.SocialAcademia.service;

import jakarta.annotation.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    private final Path uploadDir = Paths.get("uploads");

    public FileStorageService() throws IOException{
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
    }

    public String storeFile(MultipartFile file) throws IOException{
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path target = uploadDir.resolve(filename);
        Files.copy(file.getInputStream(), target);
        return filename;
    }

    public Resource loadFileResource(String filename) throws MalformedURLException {
        Path filePath = uploadDir.resolve(filename).normalize();
        UrlResource resource = new UrlResource(filePath.toUri());
        if (resource.exists()){
            return (Resource) resource;
        }else {
            throw new RuntimeException(("File not found"+ filename));
        }

    }
}
