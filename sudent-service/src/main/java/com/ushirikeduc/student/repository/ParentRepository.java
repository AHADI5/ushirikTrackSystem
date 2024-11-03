package com.ushirikeduc.student.repository;

import com.ushirikeduc.student.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Integer> {

    Optional<Parent> findByEmail(String  emailAddress);
    List<Parent> findParentBySchoolID(int schoolID)  ;
}
