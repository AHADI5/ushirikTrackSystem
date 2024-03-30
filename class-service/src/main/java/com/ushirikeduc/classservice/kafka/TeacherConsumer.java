package com.ushirikeduc.classservice.kafka;
import Dto.TeacherEvent;
import com.ushirikeduc.classservice.model.ClassRoom;
import com.ushirikeduc.classservice.model.Teacher;
import com.ushirikeduc.classservice.repository.TeacherRepository;
import com.ushirikeduc.classservice.service.ClassRoomService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TeacherConsumer {
    private final TeacherRepository teacherRepository;
    private final ClassRoomService classesService;

    public TeacherConsumer(TeacherRepository teacherRepository,
                           ClassRoomService classRoomService,
                           ClassRoomService classesService) {
        this.teacherRepository = teacherRepository;
        this.classesService = classesService;

    }

    @KafkaListener(
            topics = "create-teacher",
            groupId = "${spring.kafka.consumer.group-id}"
    )

    public  void  consume(TeacherEvent event) {
        log.info(String.format("Teacher  Event received in school service => %s", event.toString()));
        // save the teacher in the database
        Teacher teacher = Teacher.builder()
                .classID((long) event.getClassID())
                .teacherID((long) event.getTeacherID())
                .name(event.getFirstName() + " " + event.getLastName())
                .build();
        Teacher savedTeacher = teacherRepository.save(teacher);
//        log.info("Class Assigned Successfully" + savedTeacher);

        // todo : calling assign teacher to class
        classesService.assignTeacherToClass(savedTeacher);

    }
    @Bean
    public StringJsonMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }
}
