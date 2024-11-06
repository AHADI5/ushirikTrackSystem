package com.ushirikeduc.disciplineservice.service;


import com.ushirikeduc.disciplineservice.Dto.RuleRegisterRequest;
import com.ushirikeduc.disciplineservice.Dto.RuleResponse;
import com.ushirikeduc.disciplineservice.Dto.ViolationRegisterRequest;
import com.ushirikeduc.disciplineservice.Dto.ViolationResponse;
import com.ushirikeduc.disciplineservice.model.Rule;
import com.ushirikeduc.disciplineservice.model.SanctionType;
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
            for (ViolationRegisterRequest violationRegisterRequest : ruleRegisterRequest.violation()) {
                SanctionType sanctionType = getSanctionType(violationRegisterRequest);
                ViolationType violationType = ViolationType.builder()
                        .rule(savedRule)  // Utiliser la règle enregistrée
                        .occurrence(violationRegisterRequest.occurrenceNumber())
                        .sanction(violationRegisterRequest.sanctionType())
                        .sanctionType(sanctionType)
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

    private static SanctionType getSanctionType(ViolationRegisterRequest violationRegisterRequest) {
        SanctionType sanctionType = null ;
        switch (violationRegisterRequest.sanctionPredefinedType()) {
            case "Remark" -> sanctionType = SanctionType.REMARK;
            case "Temp_exclude" -> sanctionType = SanctionType.TEMP_EXCLUDE ;
            case "def_exclude" -> sanctionType = SanctionType.DEF_EXCLUDE ;
            case "invite_parent" -> sanctionType = SanctionType.INVITE_PARENT ;
            case "warning" -> sanctionType = SanctionType.WARNING;

        }
        return sanctionType;
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


    public List<RuleResponse> getRulesBySchoolID(int schoolID) {
        List<RuleResponse> ruleResponseList = new ArrayList<>() ;
        List<Rule> rules  = ruleRepository.findRuleBySchoolID(schoolID) ;

        for (Rule rule : rules ) {
            RuleResponse ruleResponse = new RuleResponse(
                    rule.getRuleId(),
                    rule.getTitle(),
                    rule.getContent(),
                    getSimpleViolation(rule.getViolationList())

            );
            ruleResponseList.add(ruleResponse);
        }

        return  ruleResponseList;
    }
}
