package com.ushirikeduc.disciplineservice.repository;

import com.ushirikeduc.disciplineservice.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleRepository extends JpaRepository<Rule, Integer > {
    public List<Rule> findRuleBySchoolID(long schoolID) ;
}
