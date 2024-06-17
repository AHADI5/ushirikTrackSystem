package com.ushirikeduc.app.kfka;

import Dto.ClassWorkEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public record KafkaListeners (){

    /*
    * Discipline Listener
    * */
    @KafkaListener(
            topics = "discipline-event-created",
            groupId = "classwork-max",
            containerFactory = "kafkaListenerContainerFactoryClasswork"
    )

    void listener(ClassWorkEvent classWorkEvent) {
        log.info("ClassWork Received in Max management  service %s" + classWorkEvent.toString() );
        //TODO REGISTER COMMUNICATION
        //SEND NOTIFICATION

    }
    /*
     * Student max listener
     *
     */

}
