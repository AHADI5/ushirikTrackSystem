package com.ushirikeduc.classservice.kafka.Serializer;


import Dto.ClassRoomEvent;
import Dto.ClassRoomEventEvent;
import Dto.ParentEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class EventSerializer implements Serializer<ClassRoomEventEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, ClassRoomEventEvent data) {
//        return new byte[0];
        try {
            if (data == null) {
                System.out.println("Null received at serializing phase");
                return null ;
            }
            System.out.println("Serializing ...");
            return objectMapper.writeValueAsBytes(data);

        } catch (Exception e) {
            throw new SerializationException("Error when Serializing ClassRoomEvent to byte");

        }
    }
}
