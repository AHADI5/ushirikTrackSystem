package com.ushirikeduc.schools.interfaces;

import com.ushirikeduc.schools.model.SchoolRules;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RulesInterface extends JpaRepository<SchoolRules,Integer> {
}
