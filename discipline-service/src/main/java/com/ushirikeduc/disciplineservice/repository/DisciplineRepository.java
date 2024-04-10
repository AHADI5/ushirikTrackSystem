package com.ushirikeduc.disciplineservice.repository;

import com.ushirikeduc.disciplineservice.model.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplineRepository extends JpaRepository<Discipline , Integer> {
    public Discipline getDisciplineByOwnerID(long ownerID);
}
