package com.ushirikeduc.schools.config;

import Dto.ClassRoomEvent;

import Dto.DisciplineCommuniqueEvent;
import com.ushirikeduc.schools.model.ClassRoom;
import com.ushirikeduc.schools.requests.CommuniqueRegisterRequest;
import com.ushirikeduc.schools.service.ClassRoomService;
import com.ushirikeduc.schools.service.CommuniqueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Slf4j
public class KafkaListeners {
    private  static  final String CLASSROOM_TOPIC = "classroom-created";
    private static  final String DISCIPLINE_TOPIC = "discipline-communique-created";
    private final ClassRoomService classRoomService;
    private final CommuniqueService communiqueService;

    public KafkaListeners(ClassRoomService classRoomService, CommuniqueService communiqueService) {
        this.classRoomService = classRoomService;
        this.communiqueService = communiqueService;
    }

    @KafkaListener(
            topics = CLASSROOM_TOPIC,
            groupId = "classroom",
            containerFactory = "kafkaListenerContainerFactoryClassRoom"
    )

    void listener(ClassRoomEvent classRoomEvent) {
        log.info("Classroom Received in school  service %s {}", classRoomEvent.toString());
        classRoomService.registerClassRoom(classRoomEvent);


    }

    @KafkaListener(
            topics = DISCIPLINE_TOPIC,
            groupId = "discipline_communique",
            containerFactory = "kafkaListenerContainerFactoryDisciplineCom"
    )

    void listener(DisciplineCommuniqueEvent disciplineCommuniqueEvent) {
        log.info("Discipline communique Received in school  service %s{}", disciplineCommuniqueEvent.toString());
        //Initialize an empty list for recipient issues


        // Create a communique request
        CommuniqueRegisterRequest communiqueRegisterRequest = new CommuniqueRegisterRequest(
                disciplineCommuniqueEvent.getTitle()  ,
                disciplineCommuniqueEvent.getContent(),
                "INDIVIDUAL" ,
                disciplineCommuniqueEvent.getTarget(),
                Collections.singletonList(disciplineCommuniqueEvent.getTarget())
        )  ;

        log.info("this is the object that should be persisted  : {}" , communiqueRegisterRequest.toString());

        //Get classroom
        ClassRoom  classRoom = classRoomService.getClassRoomByClassID((int) disciplineCommuniqueEvent.getDisciplineClassRoomId()) ;

        communiqueService.registerCommunique(Math.toIntExact(classRoom.getSchoolID()),  communiqueRegisterRequest) ;
    }


}
