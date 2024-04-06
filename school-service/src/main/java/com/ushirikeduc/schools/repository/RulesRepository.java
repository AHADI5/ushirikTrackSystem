package com.ushirikeduc.schools.repository;

import com.ushirikeduc.schools.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RulesRepository extends JpaRepository<Rule,Integer> {
}
