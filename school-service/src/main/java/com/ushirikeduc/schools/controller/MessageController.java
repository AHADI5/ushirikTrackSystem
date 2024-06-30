package com.ushirikeduc.schools.controller;

import Dto.DirectorEvent;
import Dto.SchoolCommuniqueEvent;
import com.ushirikeduc.schools.model.Communique;
import com.ushirikeduc.schools.model.Director;
import com.ushirikeduc.schools.model.Recipient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
@Slf4j
public record MessageController(
        @Qualifier("kafkaTemplateStudent")
        KafkaTemplate<String , DirectorEvent> kafkaTemplateStudent,
        @Qualifier("kafkaTemplateCommunique")
        KafkaTemplate<String , SchoolCommuniqueEvent> kafkaTemplateCommunique


        ) {

    public void publishDirector(Director director){
        String password = generatePassword(director);
        DirectorEvent directorEvent = new DirectorEvent();
        directorEvent.setFirstName(director.getFirstName());
        directorEvent.setLastName(director.getLastName());
        directorEvent.setEmail(director.getDirectorEmail());
        directorEvent.setPassword(password);
        directorEvent.setSchoolID(director.getSchoolID());

        kafkaTemplateStudent.send("director-created",directorEvent);
        log.info(String.format("Student Event created  => %s ", directorEvent));
    }

    private  String generatePassword(Director director) {
        //Generate a random number between 10 - 100
        int randomNumber = new Random().nextInt(91)+10;
        String firstName = director.getFirstName();
        String lastName = director.getLastName();
        //Combine teacher information with the generated random number to form teacher's password
        return firstName.substring(0,3) + lastName.substring(0,3) +randomNumber;
    }

    public void publishCommunique(Communique communique)  {
        SchoolCommuniqueEvent schoolCommuniqueEvent = new SchoolCommuniqueEvent();
        List<Recipient> recipientObject = communique.getRecipientIDs();
        List<String> recipientEmails = new ArrayList<>();
        for (Recipient recipient : recipientObject) {
            String email = recipient.getRecipient();
            recipientEmails.add(email);
        }
        schoolCommuniqueEvent.setSender("School");
        schoolCommuniqueEvent.setCommuniqueID(communique.getCommuniqueID());
        schoolCommuniqueEvent.setContent(communique.getContent());
        schoolCommuniqueEvent.setTitle(communique.getTitle());
        schoolCommuniqueEvent.setRecipients(recipientEmails);

        kafkaTemplateCommunique.send("communique-created",schoolCommuniqueEvent);
        log.info(String.format("Student Event created  => %s ", schoolCommuniqueEvent));
    }
}
