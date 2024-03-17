package com.ushirikeduc.users.kafka;


import Dto.TeacherEvent;
import com.ushirikeduc.users.dtoRequests.RegisterRequest;
import com.ushirikeduc.users.model.Role;
import com.ushirikeduc.users.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public record TeacherConsumer(AuthenticationService authenticationService) {
    @KafkaListener(
            topics = "create-teacher",
            groupId = "user"
    )
    public void consume(TeacherEvent teacherEvent) {
        log.info(String.format("Student Event received in UserManagement   => %s ", teacherEvent.toString()));
        Role role = Role.TEACHER;
        RegisterRequest teacherRequest  = new RegisterRequest();
        teacherRequest.setEmail(teacherEvent.getEmail());
        teacherRequest.setFirstName(teacherEvent.getFirstName());
        teacherRequest.setLastName(teacherEvent.getLastName());
        teacherRequest.setPassword(teacherEvent.getPassword());
        authenticationService.register(teacherRequest, role);
    }
    @Bean
    public StringJsonMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }
}
