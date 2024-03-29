package com.ushirikeduc.classservice.kafka;

import Dto.StudentEvent;
import com.ushirikeduc.classservice.service.ClassRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record studentConsumer(
        ClassRoomService classesService
) {
    @KafkaListener(
            topics = "create-student",
            groupId = "teacher"
    )
    public void consume(StudentEvent studentEvent) {
        log.info(String.format("Student Event received in school  => %s ", studentEvent.toString()));
        //todo find the selected class
        //todo create student and associate him with the existing class
        classesService.createStudent(studentEvent);

    }
}
