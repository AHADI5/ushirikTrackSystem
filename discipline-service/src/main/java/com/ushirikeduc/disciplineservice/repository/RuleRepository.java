package com.ushirikeduc.disciplineservice.repository;

import com.ushirikeduc.disciplineservice.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, Integer > {
}
