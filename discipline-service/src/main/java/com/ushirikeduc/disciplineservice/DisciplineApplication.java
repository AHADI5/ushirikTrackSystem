package com.ushirikeduc.disciplineservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DisciplineApplication {
    public static void main(String[] args) {
        SpringApplication.run(DisciplineApplication.class ,args);
    }
}
