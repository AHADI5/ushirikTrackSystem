package com.ushirikeduc.app.kfka;

import Dto.*;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.ushirikeduc.app.Dto.*;
import com.ushirikeduc.app.Repository.NotificationSentRepository;
import com.ushirikeduc.app.Repository.RecipientRepository;
import com.ushirikeduc.app.Service.FireBaseMessagingService;
import com.ushirikeduc.app.Service.RecipientService;
import com.ushirikeduc.app.model.NotificationMessageSent;
import com.ushirikeduc.app.model.NotificationRecipient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
@Slf4j
public record KafkaListeners (
        FireBaseMessagingService fireBaseMessagingService ,
        RecipientService recipientService ,
        RecipientRepository recipientRepository ,
        NotificationSentRepository notificationSentRepository

){

    /*
    * Discipline Listener
    * */
    @KafkaListener(
            topics = "discipline-event-created",
            groupId = "discipline",
            containerFactory = "kafkaListenerContainerFactoryDiscipline"
    )

    void listener(DisciplineEvent disciplineEvent) throws FirebaseMessagingException {
        log.info("Discipline event received in notification service   service %s" + disciplineEvent.toString() );
        //Make a notificationDto
        Map<String, String> additionalData = new HashMap<>();
        additionalData.put("ID" , String.valueOf(disciplineEvent.getId()) );
        additionalData.put("sender" , disciplineEvent.getSender() );
        additionalData.put("concern" , disciplineEvent.getConcern());
        NotificationDto notificationDto = new NotificationDto(
                disciplineEvent.getTitle(),
                disciplineEvent.getContent(),
                additionalData,
                disciplineEvent.getRecipient()
        );

        //TODO  : Check user preferences before sending the notification *

        sendNotification(notificationDto);


    }


    /*
     * Scchool communique
     *
     */
    @KafkaListener(
            topics = "communique-created",
            groupId = "communique",
            containerFactory = "kafkaListenerContainerFactoryCommunique"

    )

    void listener(SchoolCommuniqueEvent schoolCommuniqueEvent) throws FirebaseMessagingException {
        log.info("School Communique received in notification  service %s" + schoolCommuniqueEvent.toString() );
        Map<String, String> additionalData = new HashMap<>();
        additionalData.put("ID" , String.valueOf(schoolCommuniqueEvent.getCommuniqueID()) );
        additionalData.put("sender" , schoolCommuniqueEvent.getSender() );
        additionalData.put("concern" , schoolCommuniqueEvent.getSender());
        NotificationDto notificationDto = new NotificationDto(
                schoolCommuniqueEvent.getTitle(),
                schoolCommuniqueEvent.getContent(),
                additionalData,
                schoolCommuniqueEvent.getRecipients()

        );
        sendNotification(notificationDto);

    }

    /**
     * Consuming recipient with uniqueDevice key
     * */

    @KafkaListener(
            topics = "device-key-created",
            groupId = "unique-device-key",
            containerFactory = "kafkaListenerContainerFactoryRecipient"

    )
    void listener(RecepientInfoEvent recepientInfoEvent){
        log.info("unique Device key received successfully  %s" + recepientInfoEvent.toString() );

        if (recipientRepository.findNotificationRecipientByEmail(recepientInfoEvent.getEmail()) != null ){
            NotificationRecipient ExistingNotificationRecipient =
                    recipientRepository.findNotificationRecipientByEmail(recepientInfoEvent.getEmail());
                    ExistingNotificationRecipient.setUniqueDeviceKey(recepientInfoEvent.getDeviceKey());
                    recipientRepository.save(ExistingNotificationRecipient);
                    log.info("successfully updated");

        } else  {
            RecipientRegisterRequest recipientRegisterRequest = new RecipientRegisterRequest(
                    recepientInfoEvent.getEmail(),
                    recepientInfoEvent.getDeviceKey()
            );
            recipientService.registerRecipient(recipientRegisterRequest);
            log.info("successfully saved");
        }
    }


    @KafkaListener(
            topics = "max-added",
            groupId = "max-event",
            containerFactory = "kafkaListenerContainerFactoryMaxEvent"

    )
    void listener(MaxEvent maxEvent) throws FirebaseMessagingException {
        log.info("unique Device key received successfully  %s" + maxEvent.toString() );
        Map<String, String> additionalData = new HashMap<>();
        additionalData.put("ID" , String.valueOf(maxEvent.getMaxID()) );
        additionalData.put("sender" , maxEvent.getSender() );
        additionalData.put("concern" , maxEvent.getSender());
        NotificationDto notificationDto = new NotificationDto(
                maxEvent.getSender(),
                maxEvent.getTitle(),
                additionalData,
                maxEvent.getRecipient()

        );
        sendNotification(notificationDto);


    }

    @KafkaListener(
            topics = "max-added",
            groupId = "homework-event",
            containerFactory = "kafkaListenerContainerFactoryHomeWork"

    )
    void listener(HomeWorkAssignedEvent homeWorkAssignedEvent) throws FirebaseMessagingException {
        log.info("unique Device key received successfully  %s" + homeWorkAssignedEvent.toString() );
        Map<String, String> additionalData = new HashMap<>();
        additionalData.put("ID" , String.valueOf(homeWorkAssignedEvent.getHomeWorkID()) );
        additionalData.put("sender" , homeWorkAssignedEvent.getSender() );
        additionalData.put("concern" , homeWorkAssignedEvent.getSender());
        NotificationDto notificationDto = new NotificationDto(
                homeWorkAssignedEvent.getSender(),
                homeWorkAssignedEvent.getTitle(),
                additionalData,
                homeWorkAssignedEvent.getRecipient()

        );
        sendNotification(notificationDto);


    }

    @KafkaListener(
            topics = "homework-status-created",
            groupId = "homework-status-event",
            containerFactory = "kafkaListenerContainerFactoryHomeWorkStatus"

    )
    void listener(HomeWorkStatusEvent homeWorkStatusEvent) throws FirebaseMessagingException {
        log.info("Status changes successfully  %s" + homeWorkStatusEvent.toString() );
        Map<String, String> additionalData = new HashMap<>();
        additionalData.put("ID" , String.valueOf(homeWorkStatusEvent.getHomeworkID()) );
        additionalData.put("sender" , homeWorkStatusEvent.getSender() );
        additionalData.put("concern" , homeWorkStatusEvent.getSender());
        NotificationDto notificationDto = new NotificationDto(
               homeWorkStatusEvent.getSender(),
                homeWorkStatusEvent.getTitle(),
                additionalData,
                homeWorkStatusEvent.getRecipient()

        );
        sendNotification(notificationDto);


    }



    public  void sendNotification(NotificationDto notificationDto) throws FirebaseMessagingException {
        List<NotificationRecipient > notificationRecipients = new ArrayList<>();
        List<NotificationMessage> notificationMessageList = new ArrayList<>();
        for (String email : notificationDto.emails()){
            NotificationRecipient notificationRecipient = recipientRepository.findNotificationRecipientByEmail(email);
            notificationRecipients.add(notificationRecipient);
        }

        NotificationMessageSent notificationMessageSent = NotificationMessageSent
                .builder()
                .recipients(notificationRecipients)
                .content(notificationDto.content())
                .title(notificationDto.title())

                .date(new Date())
                .build();
        //SaveNotification
        NotificationMessageSent savedNotificationMessage = notificationSentRepository.save(notificationMessageSent);

        for (NotificationRecipient notificationRecipient : savedNotificationMessage.getRecipients()) {
            NotificationMessage notificationMessage = new NotificationMessage(
                    notificationRecipient.getUniqueDeviceKey(),
                    notificationRecipient.getEmail(),
                    notificationDto.title() ,
                    notificationDto.content() ,
                    notificationDto.data()
            );
            notificationMessageList.add(notificationMessage);
        }
        //Publish notification
        fireBaseMessagingService.sendNotificationsByToken (notificationMessageList);

    }
}
