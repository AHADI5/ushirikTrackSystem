package com.ushirikeduc.courseservice.kafka.Serializers;

import Dto.ClassWorkEvent;
import Dto.CourseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;


public class CourseSerializer implements Serializer<CourseEvent> {


    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, CourseEvent data) {

        try {
            if (data == null) {
                System.out.println("Null received at serializing phase");
                return null ;
            }
            System.out.println("Serializing ...");
            return objectMapper.writeValueAsBytes(data);

        } catch (Exception e) {
            throw new SerializationException("Error when Serializing Classwork Event to byte");

        }
    }

}

