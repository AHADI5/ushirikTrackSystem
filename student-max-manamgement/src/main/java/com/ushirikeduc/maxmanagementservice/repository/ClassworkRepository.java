package com.ushirikeduc.maxmanagementservice.repository;

import com.ushirikeduc.maxmanagementservice.model.ClassWorksAssigned;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassworkRepository extends JpaRepository<ClassWorksAssigned , Integer> {
    ClassWorksAssigned findClassWorksAssignedByClassWorkID(int classWorkID);
}
