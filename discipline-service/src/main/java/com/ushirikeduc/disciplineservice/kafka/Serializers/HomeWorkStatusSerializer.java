package com.ushirikeduc.disciplineservice.kafka.Serializers;

import Dto.DisciplineEvent;
import Dto.HomeWorkStatusEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;


public class HomeWorkStatusSerializer implements Serializer<HomeWorkStatusEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, HomeWorkStatusEvent data) {
        try {
            if (data == null) {
                System.out.println("Null received at serializing phase");
                return null ;
            }
            System.out.println("Serializing ...");
            return objectMapper.writeValueAsBytes(data);

        } catch (Exception e) {
            throw new SerializationException("Error when Serializing HomeworkStatus Event to byte");

        }
    }

}