package com.ushirikeduc.classservice.repository;

import com.ushirikeduc.classservice.model.ClassRoomOption;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRoomOptionRepository extends JpaRepository<ClassRoomOption , Integer> {
    List<ClassRoomOption> getClassRoomOptionBySchoolID(long schoolID);

    ClassRoomOption findClassRoomOptionByClassOptionID(long classOptionID);
}
