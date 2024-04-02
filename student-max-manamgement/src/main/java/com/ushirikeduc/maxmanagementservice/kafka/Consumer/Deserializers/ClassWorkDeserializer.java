package com.ushirikeduc.maxmanagementservice.kafka.Consumer.Deserializers;

import Dto.ClassWorkEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ClassWorkDeserializer implements Deserializer<ClassWorkEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ClassWorkEvent deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return  null ;
            }
            System.out.println("Deserializing ...");
            return  objectMapper.readValue(new String(data , StandardCharsets.UTF_8), ClassWorkEvent.class);

        } catch (Exception e) {
            throw new SerializationException("Error When deserializing byte[] to ClassworkEvent", e);
        }


    }

}
