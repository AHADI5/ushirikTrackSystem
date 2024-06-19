package com.ushirikeduc.courseservice.kafka.Serializers;

import Dto.ClassWorkEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ClassWorkSerializer implements Serializer<ClassWorkEvent> {

    /*
    *
    * this is a custom serializer for serializing ClassWorkEvent
    * this will be used in all the microservices for serializing kafka messages
    * */

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, ClassWorkEvent data) {
//        return new byte[0];
        try {
            if (data == null) {
                System.out.println("Null received at serializing phase");
                return null ;
            }
            System.out.println("Serializing ...");
            return objectMapper.writeValueAsBytes(data);

        } catch (Exception e) {
            throw new SerializationException("Error when Serializing Classwork Event to byte" + e);

        }
    }
//    @Override
//    public void close() {
//    }
}
