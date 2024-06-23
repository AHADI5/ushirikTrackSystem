package com.ushirikeduc.users.auth;

import Dto.DirectorEvent;
import Dto.RecepientInfoEvent;
import Dto.SchoolCommuniqueEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public record MessageController(

        KafkaTemplate<String , RecepientInfoEvent> kafkaTemplateUniqueDeviceKey



) {

    public void publishUniqueDeviceKey(String email,  String deviceKey){
        RecepientInfoEvent recepientInfoEvent = new RecepientInfoEvent(
                email ,
                deviceKey
        );


        kafkaTemplateUniqueDeviceKey.send("device-key-created",recepientInfoEvent);
        log.info(String.format("Recepient with unique device key  => %s ", recepientInfoEvent));
    }


}
