package com.example.SocialAcademia.controller;

import com.example.SocialAcademia.dto.PostRequest;
import com.example.SocialAcademia.dto.PostResponse;
import com.example.SocialAcademia.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public PostResponse create(@RequestBody PostRequest request){
        return postService.create(request);
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
}
