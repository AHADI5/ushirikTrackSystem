package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.controller.MessageController;
import com.ushirikeduc.schools.model.Rule;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.repository.RulesRepository;
import com.ushirikeduc.schools.repository.SchoolRepository;
import com.ushirikeduc.schools.requests.RuleRegistrationRequest;
import com.ushirikeduc.schools.requests.RuleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.apache.tomcat.util.digester.Rules;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public record RuleService (
        SchoolRepository schoolRepository ,
        SchoolService schoolService ,
        RulesRepository rulesRepository ,
        MessageController messageController
) {

    public List<RuleResponse> registerRule(int schoolID,
                                           List<RuleRegistrationRequest> request) {

        School school = schoolRepository.findById(schoolID)
                .orElseThrow( () -> new ResourceNotFoundException("No such school ID exists"));
        //Retrieving the school by ID
        List<Rule> rules = new ArrayList<>();
        for (RuleRegistrationRequest ruleRequest : request) {
            Rule rule = Rule.builder()
                    .title(ruleRequest.title())
                    .content(ruleRequest.content())
                    .disciplinaryDecision(ruleRequest.disciplinaryDecision())
                    .school(school)
                    .build();
            rules.add(rule);
            rulesRepository.save(rule);

        }

        //Publish created rules
        for (Rule rule : rules) {
            messageController.publishRule(rule);
        }
        return  simpleRuleForm(rules);
    }

    public  List<RuleResponse> getRulesBySchoolID (int schoolID) {
        School school = schoolService.getSchool(schoolID) ;
        List<Rule> rules = school.getRules();
        return  simpleRuleForm(rules);
    }

    public List<RuleResponse> simpleRuleForm  (List<Rule> rules ) {
        List<RuleResponse> ruleResponseList = new ArrayList<>() ;
        for (Rule rule : rules ) {
            RuleResponse ruleResponse = new RuleResponse(
                    rule.getTitle(),
                    rule.getContent(),
                    rule.getDisciplinaryDecision()
            );
            ruleResponseList.add(ruleResponse);
        }
        return  ruleResponseList;

    }

}
