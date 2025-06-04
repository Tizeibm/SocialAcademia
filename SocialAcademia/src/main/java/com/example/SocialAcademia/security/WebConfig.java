package com.example.SocialAcademia.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // autoriser toutes les routes
                .allowedOrigins("http://localhost:3000") // frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // méthodes autorisées
                .allowedHeaders("*") // tous les headers autorisés
                .allowCredentials(true); // autorise les cookies/token
    }
}
