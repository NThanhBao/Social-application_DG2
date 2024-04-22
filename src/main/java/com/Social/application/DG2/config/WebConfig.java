package com.Social.application.DG2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);

        registry.addMapping("/**")
                .allowedOrigins("http://192.168.199.219:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);

        registry.addMapping("/**")
                .allowedOrigins("http://192.168.1.191:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }
}

