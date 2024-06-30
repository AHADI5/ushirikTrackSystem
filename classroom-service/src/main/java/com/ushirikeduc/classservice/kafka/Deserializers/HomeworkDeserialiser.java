package com.ushirikeduc.classservice.kafka.Deserializers;

import Dto.ClassWorkEvent;
import Dto.HomeWorkEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;


public class HomeworkDeserialiser implements Deserializer<HomeWorkEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public HomeWorkEvent deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing ...");
            return objectMapper.readValue(new String(data, StandardCharsets.UTF_8),HomeWorkEvent.class);

        } catch (Exception e) {
            throw new SerializationException("Error When deserializing byte[] to homeWork");
        }

    }
}