package com.ushirikeduc.users.kafkaconfig;

import Dto.ParentEvent;
import Dto.TeacherEvent;
import com.ushirikeduc.users.dtoRequests.RegisterRequest;
import com.ushirikeduc.users.model.Role;
import com.ushirikeduc.users.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaListeners {

    final AuthenticationService authenticationService;

    public KafkaListeners( AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /*
    * Student Listener
    * */
    @KafkaListener(
            topics = "parent-created",
            groupId = "parent-user",
            containerFactory = "kafkaListenerContainerFactoryParent"

    )

    void listener(ParentEvent parentEvent) {
        log.info("Event received in ClassRoom  service %s" + parentEvent.toString() );
        Role role = Role.PARENT;
        RegisterRequest request = new RegisterRequest();
        request.setFirstName(parentEvent.getFirstName());
        request.setLastName(parentEvent.getLastName());
        request.setEmail(parentEvent.getEmail());
        request.setPassword(parentEvent.getPassword());
        authenticationService.register(request, role);

    }

    @KafkaListener(
            topics = "teacher-created",
            groupId = "teacher-user",
            containerFactory = "kafkaListenerContainerFactoryTeacher"

    )

    void listener(TeacherEvent teacherEvent) {
        log.info("Event received in ClassRoom  service %s" + teacherEvent.toString() );
        Role role = Role.TEACHER;
        RegisterRequest request = new RegisterRequest();
        request.setFirstName(teacherEvent.getFirstName());
        request.setLastName(teacherEvent.getLastName());
        request.setEmail(teacherEvent.getEmail());
        request.setPassword(teacherEvent.getPassword());
        authenticationService.register(request, role);

    }

}
