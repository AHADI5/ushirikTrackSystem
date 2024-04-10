package com.ushirikeduc.disciplineservice.service;


import com.ushirikeduc.disciplineservice.Dto.RuleRegisterRequest;
import com.ushirikeduc.disciplineservice.Dto.RuleResponse;
import com.ushirikeduc.disciplineservice.Dto.ViolationRegisterRequest;
import com.ushirikeduc.disciplineservice.Dto.ViolationResponse;
import com.ushirikeduc.disciplineservice.model.Rule;
import com.ushirikeduc.disciplineservice.model.ViolationType;
import com.ushirikeduc.disciplineservice.repository.RuleRepository;
import com.ushirikeduc.disciplineservice.repository.ViolationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public record RuleService(
        RuleRepository ruleRepository ,
        ViolationRepository violationRepository

) {
    public List<RuleResponse> registerRule(List<RuleRegisterRequest> requests) {
        List<Rule> ruleList = new ArrayList<>();
        for (RuleRegisterRequest ruleRegisterRequest : requests) {
            Rule rule = Rule.builder()
                    .title(ruleRegisterRequest.title())
                    .content(ruleRegisterRequest.content())
                    .schoolID(ruleRegisterRequest.schoolID())
                    .build();

            // Enregistrer la règle en base de données
            Rule savedRule = ruleRepository.save(rule);

            List<ViolationType> violationList = new ArrayList<>(); // Créer une nouvelle liste à chaque itération
            for (ViolationRegisterRequest violationRegisterRequest : ruleRegisterRequest.violationType()) {
                ViolationType violationType = ViolationType.builder()
                        .rule(savedRule)  // Utiliser la règle enregistrée
                        .occurrence(violationRegisterRequest.occurrenceNumber())
                        .sanction(violationRegisterRequest.sanctionType())
                        .build();
                violationRepository.save(violationType);
                violationList.add(violationType);
            }

            // Mettre à jour la liste de violations de la règle
            savedRule.setViolationList(violationList);
            ruleList.add(savedRule);
        }
        return simpleRule(ruleList);
    }

    public List<RuleResponse> simpleRule(List<Rule> rules) {
        List<RuleResponse> ruleResponses = new ArrayList<>();
        for (Rule rule : rules){
            RuleResponse ruleResponse = new RuleResponse(
                    rule.getRuleId(),
                    rule.getTitle(),
                    rule.getContent(),
                    getSimpleViolation(rule.getViolationList())
            );
            ruleResponses.add(ruleResponse);

        }

        return  ruleResponses;

    }

    public List<ViolationResponse> getSimpleViolation (List<ViolationType> violationType) {
        List<ViolationResponse> violationResponses = new ArrayList<>();
        for (ViolationType violation : violationType){
            ViolationResponse violationResponse = new ViolationResponse(
                    violation.getOccurrence(),
                    violation.getSanction()

            );
            violationResponses.add(violationResponse);
        }
        return violationResponses;


    }



}
