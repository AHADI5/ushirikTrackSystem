package com.ushirikeduc.schools.kafka;
import Dto.TeacherEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record TeacherConsumer(TeacherRepository teacherRepository,
                              ClassesService classesService
                              ) {

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
