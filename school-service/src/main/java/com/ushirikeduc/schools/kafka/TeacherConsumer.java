package com.ushirikeduc.schools.kafka;
import Dto.TeacherEvent;
import com.ushirikeduc.schools.interfaces.TeacherRepository;
import com.ushirikeduc.schools.model.Classes;
import com.ushirikeduc.schools.model.Teacher;
import com.ushirikeduc.schools.service.ClassesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public record TeacherConsumer(TeacherRepository teacherRepository,
                              ClassesService classesService) {

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )

    public  void  consume(TeacherEvent event) {
        log.info(String.format("Order Event received in school service => %s", event.toString()));
        // save the teacher in the database
        Teacher teacher = Teacher.builder()
                .classID(event.getClassID())
                .teacherID(event.getTeacherID())
                .name(event.getName())
                .build();
        Teacher savaedTeacher = teacherRepository.save(teacher);

        Optional<Classes> targetedClass = classesService.getClassById(savaedTeacher.getClassID());
        targetedClass.get().setTeacher(teacher);
        log.info("Class Assigned Successfully");

    }
    @Bean
    public StringJsonMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }
}
