package com.example.SocialAcademia.service;

import com.example.SocialAcademia.dto.ApplicationRequest;
import com.example.SocialAcademia.dto.ApplicationResponse;
import com.example.SocialAcademia.model.Application;
import com.example.SocialAcademia.model.Enum.ApplicationStatus;
import com.example.SocialAcademia.model.Enum.NotificationType;
import com.example.SocialAcademia.model.Opportunity;
import com.example.SocialAcademia.model.StudentProfile;
import com.example.SocialAcademia.repository.ApplicationRepository;
import com.example.SocialAcademia.repository.OpportunityRepository;
import com.example.SocialAcademia.repository.StudentProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final OpportunityRepository opportunityRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final FileStorageService fileStorageService;
    private final NotificationService notificationService;

    public ApplicationResponse apply(
            Long studentId,
            Long opportunityId,
            ApplicationRequest request,
            MultipartFile file
    ){
        StudentProfile student = studentProfileRepository.findById(studentId).orElseThrow(()-> new RuntimeException("Student not found"));
        Opportunity opportunity= opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));

        String fileUrl = (file != null && !file.isEmpty()) ? fileStorageService.storeFile(file) : null;
        Application application = Application.builder()
                .student(student)
                .opportunity(opportunity)
                .message(request.getMessage())
                .fileUrl(fileUrl)
                .appliedAt(LocalDateTime.now())
                .status(ApplicationStatus.PENDING)
                .build();
        Application applies= applicationRepository.save(application);
        String notif = "New Application from" + student.getFirstName() + " " + student.getLastName() +
                " for the opportunity : " + opportunity.getTitle();
        notificationService.createNotification(opportunity.getCreatedBy(), notif, NotificationType.APPLICATION);
        return mapToResponse(applies);
    }

    public List<ApplicationResponse> getApplications(Long opportunityId){
        return applicationRepository.findByOpportunityId(opportunityId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ApplicationResponse updateApplicatnStatus(Long applicationId, ApplicationStatus status){
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        app.setStatus(status);
        applicationRepository.save(app);

        String msg = "Your application for" + app.getOpportunity().getTitle() + " has been " +
                (status == ApplicationStatus.ACCEPTED ? " accepted." :
                status == ApplicationStatus.REJECTED ? "rejected.":"updated");
        notificationService.createNotification(app.getStudent().getUser(), msg, NotificationType.APPLICATION);
        return mapToResponse(app);
    }

    public List<ApplicationResponse> getAppsByStudent(Long studentId){
        return applicationRepository.findAll().stream()
                .filter(app -> app.getStudent().getId().equals(studentId))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteApplication(Long applicationId, Long studentId){
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() ->new RuntimeException(" Application not found"));

        if(!app.getStudent().getId().equals(studentId)){
            throw new RuntimeException("impossible de supprimer cette candidature");
        }

        applicationRepository.delete(app);
    }

    private ApplicationResponse mapToResponse(Application app){
        return ApplicationResponse.builder()
                .id(app.getId())
                .studentId(app.getStudent().getId())
                .studentName(app.getStudent().getFirstName()+ " " + app.getStudent().getLastName())
                .message(app.getMessage())
                .fileUrl(app.getFileUrl())
                .appliedAt(app.getAppliedAt())
                .status(app.getStatus())
                .build();
    }


}
