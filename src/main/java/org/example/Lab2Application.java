package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lab2Application {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Lab2Application.class);
        app.setAdditionalProfiles("jdbctemplate"); // активируем профиль jdbcclient
        app.run(args);
    }
}
