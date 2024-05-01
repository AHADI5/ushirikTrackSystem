package com.ushirikeduc.schools.repository;

import com.ushirikeduc.schools.model.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRoomRepository extends JpaRepository<ClassRoom , Integer> {
    public ClassRoom getClassRoomByClassID(long classID);
}
