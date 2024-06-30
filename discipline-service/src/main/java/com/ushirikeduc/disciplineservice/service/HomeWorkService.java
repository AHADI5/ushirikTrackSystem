package com.ushirikeduc.disciplineservice.service;

import Dto.HomeWorkAssignedEvent;
import com.ushirikeduc.disciplineservice.controller.MessageController;
import com.ushirikeduc.disciplineservice.model.Discipline;
import com.ushirikeduc.disciplineservice.model.HomeWorkToBeDone;
import com.ushirikeduc.disciplineservice.repository.DisciplineRepository;
import com.ushirikeduc.disciplineservice.repository.HomeWorkToBeDoneRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public record HomeWorkService(
        MessageController messageController ,
        DisciplineRepository disciplineRepository ,
        HomeWorkToBeDoneRepository homeWorkToBeDoneRepository

) {

    public  void registerHomeWorkToBeDone (HomeWorkAssignedEvent homeWorkAssignedEvent) {
        //Check whether the homeWork Exists
        HomeWorkToBeDone ExistinghomeWorkToBeDone = homeWorkToBeDoneRepository.findById(homeWorkAssignedEvent.getHomeWorkID())
                .orElseThrow(() -> new ResourceNotFoundException("Homework to be done not found"));

        if (ExistinghomeWorkToBeDone != null) {
            //Iterate all students involved
            for(int studentID :homeWorkAssignedEvent.getStudentIDList()) {
                //find student individual discipline
                Discipline discipline = disciplineRepository.findById(studentID)
                        .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
                //for every discipline's homework to be done list find the one matches with the existing one.
                for (HomeWorkToBeDone homeWorkToBeDone : discipline.getHomeWorkToBeDone()) {
                    if (ExistinghomeWorkToBeDone.equals(homeWorkToBeDone)){
                        ExistinghomeWorkToBeDone.setStatus(homeWorkToBeDone.getStatus());
                        homeWorkToBeDoneRepository.save(ExistinghomeWorkToBeDone);
                    }
                }
            }
        } else  {
            List<Discipline> disciplineList = new ArrayList<>();
            for(int studentID : homeWorkAssignedEvent.getStudentIDList()){
                Discipline discipline = disciplineRepository.getDisciplineByOwnerID(studentID);
                disciplineList.add(discipline);

            }
            HomeWorkToBeDone homeWorkToBeDone = HomeWorkToBeDone
                    .builder()
                    .title(homeWorkAssignedEvent.getTitle())
                    .disciplines(disciplineList)
                    .status(homeWorkAssignedEvent.getStatus())
                    .build();
            homeWorkToBeDoneRepository.save(homeWorkToBeDone);

        }
    }
}
