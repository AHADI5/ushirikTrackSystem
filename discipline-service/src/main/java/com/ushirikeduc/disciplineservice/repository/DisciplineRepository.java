package com.ushirikeduc.disciplineservice.repository;

import com.ushirikeduc.disciplineservice.model.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisciplineRepository extends JpaRepository<Discipline , Integer> {
    public Discipline getDisciplineByOwnerID(long ownerID);
    public List<Discipline> getDisciplineByClassRoomID(long classRoomID);

}
