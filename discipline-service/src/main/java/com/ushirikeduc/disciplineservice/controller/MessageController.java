package com.ushirikeduc.disciplineservice.controller;

import Dto.DisciplineCommuniqueEvent;
import Dto.DisciplineEvent;
import Dto.HomeWorkStatusEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public record MessageController(
        @Qualifier("kafkaTemplateDiscipline")
        KafkaTemplate<String , DisciplineEvent> kafkaTemplateDiscipline,
        @Qualifier("kafkaTemplateDisciplineCommunique")
        KafkaTemplate<String, DisciplineCommuniqueEvent> kafkaDisciplineTemplate ,
        @Qualifier("kafkaTemplateHomeWorkStatus")
        KafkaTemplate<String, HomeWorkStatusEvent> kafkaHomeWorkStatusTemplate

) {

    public void publishDiscipline(DisciplineEvent disciplineEvent){
        kafkaTemplateDiscipline.send("discipline-event-created",disciplineEvent);
        log.info("Discipline Event Created  => {} ", disciplineEvent);

    }

    public void publishDisciplineCommunique(DisciplineCommuniqueEvent disciplineCommuniqueEvent){
        kafkaDisciplineTemplate.send("discipline-communique-created",disciplineCommuniqueEvent);
        log.info("Discipline Communique Created  => {} ", disciplineCommuniqueEvent);
    }

    public void publishHomeWorkStatus(HomeWorkStatusEvent homeWorkStatusEvent){
        kafkaHomeWorkStatusTemplate.send("homework-status-created",homeWorkStatusEvent);
        log.info("Home Status event Created  => {} ", homeWorkStatusEvent);
    }
}
