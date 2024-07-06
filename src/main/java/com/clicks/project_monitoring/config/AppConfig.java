package com.clicks.project_monitoring.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    Faker faker() {
        return new Faker();
    }
}
