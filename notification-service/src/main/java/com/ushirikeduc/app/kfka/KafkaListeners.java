package com.ushirikeduc.app.kfka;

import Dto.DisciplineEvent;
import Dto.RecepientInfoEvent;
import Dto.SchoolCommuniqueEvent;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.ushirikeduc.app.Dto.DeviceKeyRequest;
import com.ushirikeduc.app.Dto.DeviceKeyResponse;
import com.ushirikeduc.app.Dto.RecipientRegisterRequest;
import com.ushirikeduc.app.Repository.NotificationSentRepository;
import com.ushirikeduc.app.Repository.RecipientRepository;
import com.ushirikeduc.app.Service.FireBaseMessagingService;
import com.ushirikeduc.app.Dto.NotificationMessage;
import com.ushirikeduc.app.Service.RecipientService;
import com.ushirikeduc.app.model.NotificationMessageSent;
import com.ushirikeduc.app.model.NotificationRecipient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    void listener(DisciplineEvent disciplineEvent) {
        log.info("ClassWork Received in Max management  service %s" + disciplineEvent.toString() );
        //TODO REGISTER COMMUNICATION
//        NotificationMessage notificationMessage = new NotificationMessage(
//
//        )
//        log.info(fireBaseMessagingService.sendNotificationByToken());

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
        List<NotificationRecipient > notificationRecipients = new ArrayList<>();
        List<NotificationMessage> notificationMessageList = new ArrayList<>();
       for (String email : schoolCommuniqueEvent.getRecipients()){
           NotificationRecipient notificationRecipient = recipientRepository.findNotificationRecipientByEmail(email);
           notificationRecipients.add(notificationRecipient);
       }

       NotificationMessageSent notificationMessageSent = NotificationMessageSent
               .builder()
               .recipients(notificationRecipients)
               .content(schoolCommuniqueEvent.getContent())
               .title(schoolCommuniqueEvent.getTitle())
               .date(new Date())
               .build();
       //SaveNotification
       NotificationMessageSent savedNotificationMessage = notificationSentRepository.save(notificationMessageSent);

       for (NotificationRecipient notificationRecipient : savedNotificationMessage.getRecipients()) {
           NotificationMessage notificationMessage = new NotificationMessage(
                   notificationRecipient.getUniqueDeviceKey(),
                   notificationRecipient.getEmail(),
                   schoolCommuniqueEvent.getTitle() ,
                   schoolCommuniqueEvent.getContent()

           );
           notificationMessageList.add(notificationMessage);

       }
        //Publish notification
        fireBaseMessagingService.sendNotificationByToken(notificationMessageList);
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
}
