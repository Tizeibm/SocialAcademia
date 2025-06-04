package com.example.SocialAcademia.controller;

import com.example.SocialAcademia.dto.OpportunityRequest;
import com.example.SocialAcademia.dto.OpportunityResponse;
import com.example.SocialAcademia.model.Enum.OpportunityType;
import com.example.SocialAcademia.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/opportunity")
@RequiredArgsConstructor
public class OpportunityController {
    private final OpportunityService opportunityService;

    @PostMapping("/{userId}")
    public OpportunityResponse createOpportunity(
            @RequestPart("data") OpportunityRequest request,
            @RequestPart("file") MultipartFile file,
            @PathVariable Long userId
    ){
        return opportunityService.createOpportunity(request, userId, file);
    }

    @GetMapping
    public List<OpportunityResponse> getall(){
        return opportunityService.getAllOpportunities();
    }

    @GetMapping("/search")
    public List<OpportunityResponse> search(@RequestParam String keyword){
        return opportunityService.search(keyword);
    }

    @GetMapping("/filter")
    public List<OpportunityResponse> filter(
            @RequestParam(required = false)OpportunityType type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(required = false) Long authorId
            ){
        return opportunityService.filter(type, start, end, authorId);
    }

    @GetMapping("/filter-advanced")
    public Page<OpportunityResponse> filterAdvanced(
            @RequestParam(required = false)OpportunityType type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(required = false) String authorName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ){
        return opportunityService.filterWithPagination(type, start, end, authorName, page, size, sortBy, direction);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        opportunityService.deleteOpportunity(id);
    }
}
