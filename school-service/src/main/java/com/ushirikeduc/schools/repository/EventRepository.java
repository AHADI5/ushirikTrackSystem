package com.ushirikeduc.schools.repository;

import Dto.StudentEvent;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.model.SchoolEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<SchoolEvent,Integer > {
    public List<SchoolEvent> findSchoolEventByStartingDateAfterAndSchool(Date startingDate, School school);
    public SchoolEvent findSchoolEventBySchoolAndStartingDate(School school, Date startingDate);
    public List<SchoolEvent> findAllBySchoolAndStartingDate(School school, Date startingDate);


}
