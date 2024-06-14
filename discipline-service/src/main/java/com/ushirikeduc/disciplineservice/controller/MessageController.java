package com.ushirikeduc.disciplineservice.controller;

import Dto.DisciplineEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageController {
    final KafkaTemplate<String , DisciplineEvent> kafkaTemplate;

    public MessageController(KafkaTemplate<String, DisciplineEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void publishDiscipline(DisciplineEvent disciplineEvent){
        kafkaTemplate.send("discipline-event-created",disciplineEvent);
        log.info(String.format("Discipline Event Created  => %s ", disciplineEvent));

    }
}
