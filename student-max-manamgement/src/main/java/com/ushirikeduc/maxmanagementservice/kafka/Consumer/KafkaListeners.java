package com.ushirikeduc.maxmanagementservice.kafka.Consumer;

import Dto.ClassWorkEvent;
import Dto.StudentEvent;
import com.ushirikeduc.maxmanagementservice.service.ClassWorkAssignedService;
import com.ushirikeduc.maxmanagementservice.service.MaxOwnerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;



@Service
@Slf4j
public class KafkaListeners {
    final ClassWorkAssignedService classWorkAssignedService ;
    final MaxOwnerService maxOWnerService;
    private  static  final String CLASSWORK_TOPIC = "classwork-created";
    public static final String STUDENT_TOPIC = "student-created";

    public KafkaListeners(ClassWorkAssignedService classWorkAssignedService, MaxOwnerService maxOWnerService) {
        this.classWorkAssignedService = classWorkAssignedService;
        this.maxOWnerService = maxOWnerService;
    }

//
    /*
    * Classwork Listener
    * */
    @KafkaListener(
            topics = CLASSWORK_TOPIC,
            groupId = "classwork-max",
            containerFactory = "kafkaListenerContainerFactoryClasswork"
    )

    void listener(ClassWorkEvent classWorkEvent) {
        log.info("ClassWork Received in Max management  service %s" + classWorkEvent.toString() );
        classWorkAssignedService.registerAssignedClassWork(classWorkEvent);
    }
    /*
     * Student listener
     *
     */
    @KafkaListener(
            topics = STUDENT_TOPIC,
            groupId = "student-max",
            containerFactory = "kafkaListenerContainerFactoryStudent"
    )

    void listener(StudentEvent studentEvent) {
        log.info("Owner Received in Max management  service %s" + studentEvent.toString() );
        maxOWnerService.RegisterOwner(studentEvent);
    }
}
