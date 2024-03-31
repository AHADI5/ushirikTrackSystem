package com.ushirikeduc.student;

import Dto.StudentEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class StudentApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }

    CommandLineRunner commandLineRunner(KafkaTemplate<String ,String> kafkaTemplate){
        return args -> {
            kafkaTemplate.send("student-created" , "");
        };
    }
}
