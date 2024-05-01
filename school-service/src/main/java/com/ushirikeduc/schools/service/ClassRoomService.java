package com.ushirikeduc.schools.service;

import Dto.ClassRoomEvent;
import com.ushirikeduc.schools.model.ClassRoom;
import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.repository.ClassRoomRepository;
import com.ushirikeduc.schools.repository.SchoolRepository;
import com.ushirikeduc.schools.requests.SchoolResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record ClassRoomService(
        ClassRoomRepository classRoomRepository,
        SchoolService schoolService ,
        SchoolRepository schoolRepository
) {
    public void  registerClassRoom (ClassRoomEvent classRoomEvent) {
        School school = schoolService.getSchool(Math.toIntExact(classRoomEvent.getSchoolID()));
        ClassRoom classRoom = ClassRoom.builder()
                .classID(classRoomEvent.getClassesID())
                .level(classRoomEvent.getLevel())
                .name(classRoomEvent.getName())
                .SchoolID(classRoomEvent.getSchoolID())
                .school(school)
                .build();

        classRoomRepository.save(classRoom);
    }

    public ClassRoom getClassRoomByID(int classRoomID) {
        return  classRoomRepository.findById(classRoomID)
                .orElseThrow(() -> new ResourceNotFoundException("ClassRoom Not found "));
    }

}
