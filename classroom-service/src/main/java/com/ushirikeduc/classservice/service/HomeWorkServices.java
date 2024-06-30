package com.ushirikeduc.classservice.service;

import com.ushirikeduc.classservice.controller.MessageController;
import com.ushirikeduc.classservice.dto.HomeWorkAssignedRegisterRequest;
import com.ushirikeduc.classservice.dto.ReportHomeWorkRequest;
import com.ushirikeduc.classservice.model.HomeWorkAssigned;
import com.ushirikeduc.classservice.model.HomeWorkStatus;
import com.ushirikeduc.classservice.model.Student;
import com.ushirikeduc.classservice.repository.EnrolledStudentRepository;
import com.ushirikeduc.classservice.repository.HomeWorkAssignedRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleState;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public record HomeWorkServices(
        HomeWorkAssignedRepository homeWorkAssignedRepository,
        EnrolledStudentRepository enrolledStudentRepository,
        ClassRoomEventService classRoomEventService ,
        MessageController messageController

) {

    public void registerNewHomeWork(HomeWorkAssignedRegisterRequest request){
        List<Student> studentList = new ArrayList<>();
        //getting the student list from the request
        for (int studentID : request.studentIDs()) {
            //get Individual Student
            Student student = enrolledStudentRepository.findById((long) studentID)
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found")) ;
            studentList.add(student) ;
        }
        //save homework
        HomeWorkAssigned homeWorkAssigned = HomeWorkAssigned.builder()
                .title(request.title())
                .dateToBeDone(classRoomEventService.parseDate(request.dateToBeDone()))
                .homeWorkStatus(HomeWorkStatus.ASSIGNED)
                .students(studentList)
                .build();
        HomeWorkAssigned savedHomeWorkAssigned =  homeWorkAssignedRepository.save(homeWorkAssigned);

        //Publish homeWork Assigned
        messageController.publishHomeWork(savedHomeWorkAssigned);
    }

    public void reportHomeWork(ReportHomeWorkRequest request ) {
       /*
       * This function update homework status
       * */
        HomeWorkAssigned homeWorkAssigned = homeWorkAssignedRepository.findById(request.homeWorkID())
                .orElseThrow(() -> new RuntimeException("HomeWorkID not found"));
        HomeWorkStatus homeWorkStatus = homeWorkAssigned.getHomeWorkStatus();
        switch (request.toString()) {
            case "Done" -> homeWorkStatus = HomeWorkStatus.DONE ;
            case "No" -> homeWorkStatus = HomeWorkStatus.NO_DONE ;
            case "Late" -> homeWorkStatus = HomeWorkStatus.DONE_LATE;
        }
        Student student = enrolledStudentRepository.findById((long) request.studentID())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        for (HomeWorkAssigned homeWork : student.getHomeWorks()) {
            if (homeWork.equals(homeWorkAssigned)) {
                homeWorkAssigned.setHomeWorkStatus(homeWorkStatus);
                HomeWorkAssigned homeWorkAssignedUpdated = homeWorkAssignedRepository.save(homeWorkAssigned);
                //Publish homeWork with the new status
                messageController.publishHomeWork(homeWorkAssignedUpdated);
            }
        }



    }


}
