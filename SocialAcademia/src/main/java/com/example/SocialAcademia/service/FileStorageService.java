package com.example.SocialAcademia.service;

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
    private final Path uploadDir = Paths.get("uploads");

    public FileStorageService() throws IOException{
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
    }

    public String storeFile(MultipartFile file) {
        try {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path target = uploadDir.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        }catch (IOException e){
            throw new RuntimeException("Error while saving ",e);
        }
    }

    public UrlResource loadFileResource(String filename)   {
        try {
            Path filePath = uploadDir.resolve(filename).normalize();
            return new UrlResource(filePath.toUri());
        }catch (MalformedURLException e){
            throw new RuntimeException("\"File not found\"+ filename Message",e);
        }

    }
}
