package com.ushirikeduc.schoolrules.controller;

import com.ushirikeduc.schoolrules.model.Rules;
import com.ushirikeduc.schoolrules.services.RulesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/rules")
public record RulesController(RulesService rulesService) {
    @PostMapping
    public void registerRule(@RequestBody RuleRegistrationRequest ruleRegistrationRequest) {
        log.info("Register new Rule {}" , ruleRegistrationRequest);
        rulesService.registerRule(ruleRegistrationRequest);
    }
    @GetMapping(path = "{idRule}")
    public Optional<Rules> getRuleById(@PathVariable("idRule") Integer idRule) {
        return rulesService.getById(idRule);
    }

}
