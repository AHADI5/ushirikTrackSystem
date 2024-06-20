package com.ushirikeduc.app.kfka;

import Dto.ClassWorkEvent;
import Dto.DisciplineEvent;
import com.ushirikeduc.app.Service.FireBaseMessagingService;
import com.ushirikeduc.app.model.NotificationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public record KafkaListeners (
        FireBaseMessagingService fireBaseMessagingService
){

    /*
    * Discipline Listener
    * */
    @KafkaListener(
            topics = "discipline-event-created",
            groupId = "discipline",
            containerFactory = "kafkaListenerContainerFactoryDiscipline"
    )

    void listener(DisciplineEvent disciplineEvent) {
        log.info("ClassWork Received in Max management  service %s" + disciplineEvent.toString() );
        //TODO REGISTER COMMUNICATION
//        NotificationMessage notificationMessage = new NotificationMessage(
//
//        )
//        log.info(fireBaseMessagingService.sendNotificationByToken());

    }
    /*
     * Student max listener
     *
     */

}
