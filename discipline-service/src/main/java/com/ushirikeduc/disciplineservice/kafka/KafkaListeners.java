package com.ushirikeduc.disciplineservice.kafka;


import Dto.StudentEvent;
import com.ushirikeduc.disciplineservice.service.DisciplineService;
import com.ushirikeduc.disciplineservice.service.RuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class KafkaListeners {
    final DisciplineService disciplineService;
    public static final String STUDENT_TOPIC = "student-created";

    public KafkaListeners(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }


    /*
     * Student listener
     *
     */
    @KafkaListener(
            topics = STUDENT_TOPIC,
            groupId = "discipline",
            containerFactory = "kafkaListenerContainerFactoryStudent"
    )

    void listener(StudentEvent studentEvent) {
        log.info("Student Received in Max management  service %s" + studentEvent.toString() );
        disciplineService.registerDiscipline(studentEvent);
    }
}
