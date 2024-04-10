package com.ushirikeduc.disciplineservice.repository;

import com.ushirikeduc.disciplineservice.model.Rule;
import com.ushirikeduc.disciplineservice.model.ViolationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViolationRepository extends JpaRepository<ViolationType,Integer> {
   public ViolationType findViolationTypeByOccurrenceAndRule(int occurrence, Rule rule);
}
