package com.ushirikeduc.users.kafkaconfig.Deserializers;

import Dto.ClassWorkEvent;
import Dto.TeacherEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

public class TeacherDeserializer implements Deserializer<TeacherEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public TeacherEvent deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return  null ;
            }
            System.out.println("Deserializing ...");
            return  objectMapper.readValue(new String(data , StandardCharsets.UTF_8), TeacherEvent.class);

        } catch (Exception e) {
            throw  new SerializationException("Error When deserializing byte[] to TeacherEvent");
        }

    }
}
