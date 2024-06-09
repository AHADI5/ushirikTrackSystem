package com.ushirikeduc.maxmanagementservice.service;

import Dto.ClassWorkEvent;
import com.ushirikeduc.maxmanagementservice.model.ClassWorksAssigned;
import com.ushirikeduc.maxmanagementservice.repository.ClassworkRepository;
import org.springframework.stereotype.Service;

@Service
public record ClassWorkAssignedService(
        ClassworkRepository classworkRepository

) {

    public void registerAssignedClassWork(ClassWorkEvent classWorkEvent) {
        ClassWorksAssigned classwork = ClassWorksAssigned.builder()

                .courseName(classWorkEvent.getCourseName())
                .classWorkType(classWorkEvent.getClassWorkType())
                .classRoomID(classWorkEvent.getClassID())
                .courseID(classWorkEvent.getCourseID())
                .classWorkID(classWorkEvent.getClassWorkID())
                .build();
        classworkRepository.save(classwork);
    }
}
