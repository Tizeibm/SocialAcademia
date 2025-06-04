package com.example.SocialAcademia.controller;

import com.example.SocialAcademia.dto.PostRequest;
import com.example.SocialAcademia.dto.PostResponse;
import com.example.SocialAcademia.model.Post;
import com.example.SocialAcademia.service.FileStorageService;
import com.example.SocialAcademia.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final FileStorageService fileStorageService;


    @GetMapping("/search")
    public Page<PostResponse> searchPosts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) Long authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage = postService.searchPosts(keyword, startDate, endDate, authorId, pageable);

        return postsPage.map(postService::map);
    }

    @PostMapping
    public PostResponse create(@RequestBody PostRequest request,
    @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        String attachmentPath = null;
        if (file != null && !file.isEmpty()) {
            attachmentPath = fileStorageService.storeFile(file);
        }

        return postService.create(request, attachmentPath);
    }

    @GetMapping
    public List<PostResponse> getAll(){
        return postService.getAll();
    }

    @GetMapping("/{id}")
    public PostResponse getById(@PathVariable Long id){
        return postService.getById(id);
    }

    @PutMapping("/{id}")
    public PostResponse update(@PathVariable Long id, @RequestBody PostRequest request){
        return postService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        postService.delete(id);
    }

    @GetMapping("/feed")
    public List<PostResponse> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return postService.getFeedPost(page, size);
    }

    @GetMapping("/posts/{postId}/attachment")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable Long postId) throws MalformedURLException {
        Post post = postService.getPostById(postId); // méthode à implémenter dans PostService

        if (post.getAttachmentPath() == null) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = fileStorageService.loadFileResource(post.getAttachmentPath());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
