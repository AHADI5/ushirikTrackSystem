package com.ushirikeduc.users.kafka;

import Dto.DirectorEvent;
import com.ushirikeduc.users.dtoRequests.RegisterRequest;
import com.ushirikeduc.users.model.Role;
import com.ushirikeduc.users.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public record DirectorConsumer(
        AuthenticationService usersService
) {
    @KafkaListener(
            topics = "create-director",
            groupId = "user"
    )
    public void consume(DirectorEvent directorEvent) {
        log.info(String.format("Student Event received in school  => %s ", directorEvent.toString()));
        Role role = Role.DIRECTOR;
        RegisterRequest DirectorRequest  = new RegisterRequest();
        DirectorRequest.setEmail(directorEvent.getEmail());
        DirectorRequest.setFirstName(directorEvent.getFirstName());
        DirectorRequest.setLastName(directorEvent.getLastName());
        DirectorRequest.setPassword(directorEvent.getPassword());
        usersService.register(DirectorRequest, role);
    }

}
