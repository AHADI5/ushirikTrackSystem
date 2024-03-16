package com.ushirikeduc.users.kafka;

import Dto.DirectorEvent;
import Dto.StudentEvent;
import com.ushirikeduc.users.service.AuthenticationService;
import com.ushirikeduc.users.services.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public record DirectorConsumer(
        AuthenticationService usersService
) {
    @KafkaListener(
            topics = "create-student",
            groupId = "teacher"
    )
    public void consume(DirectorEvent director) {
        log.info(String.format("Student Event received in school  => %s ", studentEvent.toString()));
        //todo find the selected class
        //todo create student and associate him with the existing class

        usersService.register(director);

    }
}
