package com.ushirikeduc.disciplineservice.kafka.Serializers;

import Dto.DisciplineCommuniqueEvent;
import Dto.DisciplineEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;


public class DisciplineCommuniqueSerializer implements Serializer<DisciplineCommuniqueEvent> {

    /*
     *
     * this is a custom serializer for serializing ClassWorkEvent
     * this will be used in all the microservices for serializing kafka messages
     * */

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, DisciplineCommuniqueEvent data) {
//        return new byte[0];
        try {
            if (data == null) {
                System.out.println("Null received at serializing phase");
                return null ;
            }
            System.out.println("Serializing ...");
            return objectMapper.writeValueAsBytes(data);

        } catch (Exception e) {
            throw new SerializationException("Error when Serializing Discipline Event to byte");

        }
    }
//    @Override
//    public void close() {
//    }
}
