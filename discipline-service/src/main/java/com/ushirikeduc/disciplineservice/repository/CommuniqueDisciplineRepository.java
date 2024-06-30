package com.ushirikeduc.disciplineservice.repository;

import com.ushirikeduc.disciplineservice.model.DisciplineCommunique;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommuniqueDisciplineRepository extends JpaRepository<DisciplineCommunique , Integer> {

}
