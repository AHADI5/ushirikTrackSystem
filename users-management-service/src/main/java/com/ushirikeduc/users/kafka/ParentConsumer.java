package com.ushirikeduc.users.kafka;

import Dto.ParentEvent;
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
public record ParentConsumer(AuthenticationService usersService) {
    @KafkaListener(
            topics = "create-parent",
            groupId = "user"
    )
    public void consume(ParentEvent parentEvent) {
        log.info(String.format("Student Event received in school  => %s ", parentEvent.toString()));
        Role role = Role.PARENT;
        RegisterRequest parentRequest  = new RegisterRequest();
        parentRequest.setEmail(parentEvent.getEmail());
        parentRequest.setFirstName(parentEvent.getFirstName());
        parentRequest.setLastName(parentEvent.getLastName());
        parentRequest.setPassword(parentEvent.getPassword());
        usersService.register(parentRequest,role);
    }
}




