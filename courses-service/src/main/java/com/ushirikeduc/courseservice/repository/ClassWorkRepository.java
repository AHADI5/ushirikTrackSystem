package com.ushirikeduc.courseservice.repository;

import com.ushirikeduc.courseservice.model.ClassWork;
import com.ushirikeduc.courseservice.model.ClassworkType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassWorkRepository extends JpaRepository<ClassWork , Integer> {
    List<ClassWork> getClassWorkByClassworkType(ClassworkType classworkType) ;
}
