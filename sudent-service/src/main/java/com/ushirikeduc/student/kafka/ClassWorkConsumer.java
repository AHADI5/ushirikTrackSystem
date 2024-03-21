package com.ushirikeduc.student.kafka;

import Dto.ClassWorkEvent;
import Dto.StudentEvent;
import com.ushirikeduc.student.repository.ClassWorkAssignedRepository;
import com.ushirikeduc.student.services.ClassWorkAssignedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record ClassWorkConsumer(
        ClassWorkAssignedService classWorkService

) {
    @KafkaListener(
            topics = "create-student",
            groupId = "teacher"
    )
    public void consume(ClassWorkEvent  classWorkEvent) {
        log.info(String.format("Student Event received in Student  => %s ", classWorkEvent.toString()));
        //todo find the selected class
        //todo create student and associate him with the existing class
        classWorkService.registerAssignedClassWork(classWorkEvent);

    }
}