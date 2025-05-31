package com.example.SocialAcademia.controller;

import com.example.SocialAcademia.dto.CommentRequest;
import com.example.SocialAcademia.dto.CommentResponse;
import com.example.SocialAcademia.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/posts/{id}/comments")
    public CommentResponse create(@RequestBody CommentRequest request, @PathVariable Long id){
        return commentService.create(id, request);
    }

    @GetMapping("/posts/{id}/comments")
    public List<CommentResponse> getByPost(@PathVariable Long id){
        return commentService.getByPost(id);
    }

    @PutMapping("/comments/{id}")
    public CommentResponse update(@PathVariable Long id, @RequestBody CommentRequest request){
        return commentService.Update(id, request);
    }
    @DeleteMapping("/comments/{id}")
    public void delete(@PathVariable Long id){
        commentService.delete(id);
    }
}
