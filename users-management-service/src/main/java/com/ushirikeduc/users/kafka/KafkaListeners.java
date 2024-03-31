package com.ushirikeduc.users.kafka;

import Dto.CourseEvent;
import Dto.ParentEvent;
import Dto.TeacherEvent;
import com.ushirikeduc.users.dtoRequests.RegisterRequest;
import com.ushirikeduc.users.model.Role;
import com.ushirikeduc.users.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListeners {
    final AuthenticationService usersService;

    public KafkaListeners( AuthenticationService usersService) {
        this.usersService = usersService;

    }

    /*
    * Parent Listener
    * */
    @KafkaListener(
            topics = "parent-created",
            groupId = "user"
//            containerFactory = "kafkaListenerContainerFactory"

    )

    void listener(ParentEvent parentEvent) {
        log.info(String.format("Parent Event received in users management  => %s ", parentEvent.toString()));
        Role role = Role.PARENT;
        RegisterRequest parentRequest  = new RegisterRequest();
        parentRequest.setEmail(parentEvent.getEmail());
        parentRequest.setFirstName(parentEvent.getFirstName());
        parentRequest.setLastName(parentEvent.getLastName());
        parentRequest.setPassword(parentEvent.getPassword());
        usersService.register(parentRequest,role);
    }

    /*
     * Teacher Listener
     *
     */
    @KafkaListener(
            topics = "teacher-created",
            groupId = "user"
//            containerFactory = "kafkaListenerContainerFactory"

    )

    void listener(TeacherEvent teacherEvent) {
        log.info(String.format("Teacher Event received in UserManagement   => %s ", teacherEvent.toString()));
        Role role = Role.TEACHER;
        RegisterRequest teacherRequest  = new RegisterRequest();
        teacherRequest.setEmail(teacherEvent.getEmail());
        teacherRequest.setFirstName(teacherEvent.getFirstName());
        teacherRequest.setLastName(teacherEvent.getLastName());
        teacherRequest.setPassword(teacherEvent.getPassword());
        usersService.register(teacherRequest, role);

    }



}
