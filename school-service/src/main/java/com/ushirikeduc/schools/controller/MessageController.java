package com.ushirikeduc.schools.controller;

import Dto.ParentEvent;
import Dto.RuleEvent;
import Dto.StudentEvent;

import com.ushirikeduc.schools.model.Rule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@Slf4j
public record MessageController(

        KafkaTemplate<String , RuleEvent> kafkaTemplateRule


        ) {

    public void publishRule(Rule rule){
        RuleEvent ruleEvent = new RuleEvent();
        ruleEvent.setRuleID(rule.getRuleId());
        ruleEvent.setDisciplinaryDecision(rule.getDisciplinaryDecision());
        ruleEvent.setTitle(ruleEvent.getTitle());
        kafkaTemplateRule.send("rule-created",ruleEvent);
        log.info(String.format("Student Event created  => %s ", ruleEvent));
    }
}
