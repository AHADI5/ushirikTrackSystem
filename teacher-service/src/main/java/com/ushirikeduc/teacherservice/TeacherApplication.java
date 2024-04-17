package com.ushirikeduc.teacherservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TeacherApplication {
//    @Bean
//    public WebClient webClient() {
//        return WebClient.builder().build();
//    }

    public static void main(String[] args) {
        SpringApplication.run(TeacherApplication.class, args);
    }


}
