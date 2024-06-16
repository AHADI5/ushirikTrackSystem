package com.ushirikeduc.disciplineservice.repository;

import com.ushirikeduc.disciplineservice.Dto.IncidentRegisterRequest;
import com.ushirikeduc.disciplineservice.Dto.IncidentResponse;
import com.ushirikeduc.disciplineservice.model.Discipline;
import com.ushirikeduc.disciplineservice.model.Incident;
import com.ushirikeduc.disciplineservice.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident , Integer> {
    public List<Incident> getIncidentByDiscipline_OwnerID(long studentID);
    public List<Incident> getAllByDisciplineOwnerIDAndRuleBypassed(long ownerID , Rule rule);

    public  List<Incident> getIncidentByDiscipline(Discipline discipline) ;
}
