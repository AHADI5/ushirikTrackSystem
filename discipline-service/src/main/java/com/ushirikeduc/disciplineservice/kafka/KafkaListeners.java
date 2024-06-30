package com.ushirikeduc.disciplineservice.kafka;


import Dto.HomeWorkAssignedEvent;
import Dto.StudentEvent;
import com.ushirikeduc.disciplineservice.repository.HomeWorkToBeDoneRepository;
import com.ushirikeduc.disciplineservice.service.DisciplineService;
import com.ushirikeduc.disciplineservice.service.HomeWorkService;
import com.ushirikeduc.disciplineservice.service.RuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class KafkaListeners {
    final DisciplineService disciplineService;
    final HomeWorkToBeDoneRepository homeWorkToBeDoneRepository;
    final HomeWorkService homeWorkService ;
    public static final String STUDENT_TOPIC = "student-created";

    public KafkaListeners(DisciplineService disciplineService, HomeWorkToBeDoneRepository homeWorkToBeDoneRepository, HomeWorkService homeWorkService) {
        this.disciplineService = disciplineService;
        this.homeWorkToBeDoneRepository = homeWorkToBeDoneRepository;
        this.homeWorkService = homeWorkService;
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

    /**
     *
     * Assigned Homework event
     * */
    @KafkaListener(
            topics = STUDENT_TOPIC,
            groupId = "homeworkAssigned",
            containerFactory = "kafkaListenerContainerFactoryHomeWork"
    )

    void listener(HomeWorkAssignedEvent homeWorkAssignedEvent) {
        log.info("Homework Received in discipline  service %s" + homeWorkAssignedEvent.toString() );
        homeWorkService.registerHomeWorkToBeDone(homeWorkAssignedEvent);
    }
}


