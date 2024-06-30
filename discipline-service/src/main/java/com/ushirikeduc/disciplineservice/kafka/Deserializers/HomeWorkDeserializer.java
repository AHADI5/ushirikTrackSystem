package com.ushirikeduc.disciplineservice.kafka.Deserializers;

import Dto.HomeWorkAssignedEvent;
import Dto.HomeWorkEvent;
import Dto.StudentEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;


public class HomeWorkDeserializer implements Deserializer<HomeWorkAssignedEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public HomeWorkAssignedEvent deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return  null ;
            }
            System.out.println("Deserializing ...");
            return  objectMapper.readValue(new String(data , StandardCharsets.UTF_8), HomeWorkAssignedEvent.class);

        } catch (Exception e) {
            throw  new SerializationException("Error When deserializing byte[] to Homework");
        }

    }
}