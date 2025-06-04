package com.example.SocialAcademia.service;

import com.example.SocialAcademia.dto.OpportunityRequest;
import com.example.SocialAcademia.dto.OpportunityResponse;
import com.example.SocialAcademia.model.Enum.NotificationType;
import com.example.SocialAcademia.model.Enum.OpportunityType;
import com.example.SocialAcademia.model.Opportunity;
import com.example.SocialAcademia.model.User;
import com.example.SocialAcademia.repository.OpportunityRepository;
import com.example.SocialAcademia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpportunityService {
    private final OpportunityRepository opportunityRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final NotificationService notificationService;

    public OpportunityResponse createOpportunity(OpportunityRequest request, Long userId, MultipartFile file){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        String fileUrl = fileStorageService.storeFile(file);
        Opportunity opportunity = Opportunity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .type(request.getType())
                .createdBy(user)
                .CreatedAt(LocalDateTime.now())
                .fileUrl(fileUrl)
                .build();
        notificationService.createNotification(user,"New Opportunity", NotificationType.OPPORTUNITY );
        Opportunity saved = opportunityRepository.save(opportunity);

        return mapToResponse(saved);
    }

    public void deleteOpportunity(Long opportunityId){
        opportunityRepository.deleteById(opportunityId);
    }

    public List<OpportunityResponse> getAllOpportunities(){
        return opportunityRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<OpportunityResponse> search( String keyword){
        return opportunityRepository.findByTitleContainingIgnoreCase(keyword).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<OpportunityResponse> filter(
            OpportunityType type,
            LocalDate startDate,
            LocalDate endDate,
            Long authorId
    ){
        List<Opportunity> list = opportunityRepository.filterOpportunity(type, startDate, endDate, authorId);
        return list.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Page<OpportunityResponse> filterWithPagination(
            OpportunityType type,
            LocalDate startDate,
            LocalDate endDate,
            String authorName,
            int page,
            int size,
            String sortBy,
            String direction
    ){
        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending(): Sort.by(sortBy).ascending());
        Specification<Opportunity> specification = OpportunitySpecification.withFilter(type, startDate, endDate, authorName);
        return opportunityRepository.findAll(specification, pageable).map(this::mapToResponse);
    }

    private OpportunityResponse mapToResponse(Opportunity opp){
        return OpportunityResponse.builder()
                .id(opp.getId())
                .title(opp.getTitle())
                .description(opp.getDescription())
                .type(opp.getType())
                .date(opp.getDate())
                .createdByName(opp.getCreatedBy().getUsername())
                .createdAt(opp.getCreatedAt())
                .build();
    }
}
