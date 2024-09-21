package com.ushirikeduc.schools.config.Deserializers;

import Dto.ClassRoomEvent;
import Dto.DisciplineCommuniqueEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

public class DisciplineCommuniqueDeserializer implements Deserializer<DisciplineCommuniqueEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public DisciplineCommuniqueEvent deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return  null ;
            }
            System.out.println("Deserializing ...");
            return  objectMapper.readValue(new String(data , StandardCharsets.UTF_8), DisciplineCommuniqueEvent.class);

        } catch (Exception e) {
            throw  new SerializationException("Error When deserializing byte[] to DisciplineCommuniqueEvent");
        }

    }
}