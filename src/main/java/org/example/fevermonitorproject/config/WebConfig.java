package org.example.fevermonitorproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class WebConfig implements WebMvcConfigurer {

    @Override

    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS for frontend
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Allow requests from this origin
                .allowedOrigins("http://192.168.1.40:3000") // Allow requests from this origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed methods
                .allowedHeaders("*"); // Allow all headers
    }
}