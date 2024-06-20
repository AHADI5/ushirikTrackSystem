package com.ushirikeduc.app;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@SpringBootApplication
public class NotificationApplication {
    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        GoogleCredentials googleCredentials= GoogleCredentials
                .fromStream(new ClassPathResource("ushirik-educ-system-firebase-adminsdk-4yn7u-ebbf3a88b3.json").getInputStream()
        );
        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials).build();
        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "ushirik-educ");
        return  FirebaseMessaging.getInstance(app);
    }
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }
}