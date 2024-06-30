package com.ushirikeduc.maxmanagementservice.controller;

import Dto.MaxEvent;
import com.ushirikeduc.maxmanagementservice.model.MaxOwner;
import com.ushirikeduc.maxmanagementservice.model.Score;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public record MessageController(

        KafkaTemplate<String , MaxEvent> kafkaTemplateMax


) {

    public void publishMax(Score score){
        List<String> emails = new ArrayList<>();


     MaxEvent maxEvent = new MaxEvent() ;
     maxEvent.setMaxID(Math.toIntExact(score.getId()));
     maxEvent.setMax(score.getScore());
     maxEvent.setConcern("Max");
     maxEvent.setTitle("Une nouvelle cote a ete atribuee");
     maxEvent.setSender("StudentMax");
     emails.add(score.getStudent().getParentEmail());

     maxEvent.setRecipient(emails);
        kafkaTemplateMax.send("max-added",maxEvent);
        log.info(String.format("Student Event created  => %s ", maxEvent ));
    }




}
