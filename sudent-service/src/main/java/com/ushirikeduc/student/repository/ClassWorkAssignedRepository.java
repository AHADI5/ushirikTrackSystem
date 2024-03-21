package com.ushirikeduc.student.repository;

import com.ushirikeduc.student.model.ClassWorksAssigned;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassWorkAssignedRepository extends JpaRepository<ClassWorksAssigned, Integer> {
    public  ClassWorksAssigned findClassWorksAssignedByClassWorkID(int classWorkID) ;
}
