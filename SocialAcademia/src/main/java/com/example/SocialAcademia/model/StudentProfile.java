package com.example.SocialAcademia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String school;
    private String degree;
    private String year;

    @ElementCollection
    private List<String> skills;

    private String cvUrl;
    @OneToOne
    @JoinColumn(name  = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSchool() {
        return school;
    }

    public List<String> getSkills() {
        return skills;
    }

    public String getDegree() {
        return degree;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public String getYear() {
        return year;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
