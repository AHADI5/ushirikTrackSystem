package com.ushirikeduc.courseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CoursesApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoursesApplication.class, args);
    }
}
