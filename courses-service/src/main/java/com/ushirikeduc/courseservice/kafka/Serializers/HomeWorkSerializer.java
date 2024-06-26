package com.ushirikeduc.courseservice.kafka.Serializers;

import Dto.CourseEvent;
import Dto.HomeWorkEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class HomeWorkSerializer implements Serializer<HomeWorkEvent> {


    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, HomeWorkEvent data) {

        try {
            if (data == null) {
                System.out.println("Null received at serializing phase");
                return null ;
            }
            System.out.println("Serializing ...");
            return objectMapper.writeValueAsBytes(data);

        } catch (Exception e) {
            throw new SerializationException("Error when Serializing Homework Event to byte");

        }
    }

}
