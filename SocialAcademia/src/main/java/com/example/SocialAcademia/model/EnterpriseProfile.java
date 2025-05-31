
package com.example.SocialAcademia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnterpriseProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String sector;
    private String website;
    private String description;
    private String profilePictureUrl;
    private String email;

    @OneToOne
    @JoinColumn(name  = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDescription() {
        return description;
    }

    public String getSector() {
        return sector;
    }

    public User getUser() {
        return user;
    }

    public String getWebsite() {
        return website;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
