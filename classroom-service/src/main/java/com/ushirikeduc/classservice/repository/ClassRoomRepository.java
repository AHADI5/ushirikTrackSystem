package com.ushirikeduc.classservice.repository;

import com.ushirikeduc.classservice.model.ClassRoom;
import com.ushirikeduc.classservice.model.ClassRoomOption;
import com.ushirikeduc.classservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    List<ClassRoom> getClassRoomBySchoolID(long schoolID) ;
    List<ClassRoom> getClassRoomByLevel(Long level);

    List<ClassRoom> getClassRoomByClassRoomOption(ClassRoomOption classRoomOption);
    List<ClassRoom> getAllBySchoolID(long schoolID);

    List<ClassRoom> getClassRoomsBySchoolID(long schoolID);



}
