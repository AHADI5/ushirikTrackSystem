package com.ushirikeduc.maxmanagementservice.repository;

import com.ushirikeduc.maxmanagementservice.model.ClassWorksAssigned;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassworkRepository extends JpaRepository<ClassWorksAssigned , Integer> {
    ClassWorksAssigned findClassWorksAssignedByClassWorkID(int classWorkID);


     List<ClassWorksAssigned> findClassWorksAssignedByClassRoomID(long classRoomID);


}
