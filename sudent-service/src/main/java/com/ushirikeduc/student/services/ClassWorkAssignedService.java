package com.ushirikeduc.student.services;

import Dto.ClassWorkEvent;
import com.ushirikeduc.student.model.ClassWorksAssigned;
import com.ushirikeduc.student.repository.ClassWorkAssignedRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public record ClassWorkAssignedService(
        ClassWorkAssignedService classWorkService,
        ClassWorkAssignedRepository classWorkRepo

) {

    public void registerAssignedClassWork(ClassWorkEvent classWorkEvent) {
        ClassWorksAssigned classwork = ClassWorksAssigned.builder()
                .title(classWorkEvent.getTitle())
                .courseName(classWorkEvent.getCourseName())
                .courseID(classWorkEvent.getCourseID())
                .classWorkID(classWorkEvent.getClassWorkID())
                .build();
        classWorkRepo.save(classwork);
    }
}
