package com.example.SocialAcademia.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String department;
    private String university;
    private String phone;
    private String office;
    @ElementCollection
    private List<String> researchTopics;

    @OneToOne
    @JoinColumn(name  = "user_id")
    private User user;

}
