package com.ushirikeduc.maxmanagementservice.kafka.Consumer;

import Dto.ClassWorkEvent;
import Dto.StudentEvent;
import com.ushirikeduc.maxmanagementservice.service.ClassWorkAssignedService;
import com.ushirikeduc.maxmanagementservice.service.MaxOwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class KafkaListeners {

    final ClassWorkAssignedService classWorkAssignedService ;
    final MaxOwnerService maxOWnerService;

    public KafkaListeners(ClassWorkAssignedService classWorkAssignedService, MaxOwnerService maxOWnerService) {
        this.classWorkAssignedService = classWorkAssignedService;
        this.maxOWnerService = maxOWnerService;
    }


    /*
    * Student Listener
    * */
    @KafkaListener(
            topics = "classwork-created",
            groupId = "classwork"
    )

    void listener(ClassWorkEvent classWorkEvent) {
        log.info("ClassWork Received in Max management  service %s" + classWorkEvent.toString() );
        classWorkAssignedService.registerAssignedClassWork(classWorkEvent);
    }

    /*
     * Classwork listener
     *
     */
    @KafkaListener(
            topics = "student-created",
            groupId = "student"
//            containerFactory = "kafkaListenerContainerFactory"

    )

    void listener(StudentEvent studentEvent) {
        log.info("Owner Received in Max management  service %s" + studentEvent.toString() );
        maxOWnerService.RegisterOwner(studentEvent);
    }


}
