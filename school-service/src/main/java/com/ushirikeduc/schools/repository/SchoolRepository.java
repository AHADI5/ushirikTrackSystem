package com.ushirikeduc.schools.repository;

import com.ushirikeduc.schools.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {
    public List<School> getSchoolByAdministrator_Email(String adminEmail);
    public School getSchoolByDirector_DirectorEmail(String directorEmail) ;

}
