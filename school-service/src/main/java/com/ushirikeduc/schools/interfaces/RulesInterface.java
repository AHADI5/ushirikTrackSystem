package com.ushirikeduc.schools.interfaces;

import com.ushirikeduc.schools.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RulesInterface extends JpaRepository<Rule,Integer> {
}
