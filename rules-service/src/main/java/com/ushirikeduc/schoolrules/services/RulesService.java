package com.ushirikeduc.schoolrules.services;

import com.ushirikeduc.schoolrules.controller.RuleRegistrationRequest;
import com.ushirikeduc.schoolrules.model.Rules;
import com.ushirikeduc.schoolrules.rulesrepository.RulesInterface;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record RulesService(RulesInterface rulesInterface) {
    public  void registerRule(RuleRegistrationRequest request) {
        Rules rules = Rules.builder()
                .content(request.content())
                .title(request.title())
                .domain(request.domain())
                .status(request.status())
                .schoolId(request.schoolId())
                .build();

        rulesInterface.save(rules);
    }
    public Optional<Rules> getById(int RuleId) {
        return rulesInterface.findById(RuleId);
    }
}
