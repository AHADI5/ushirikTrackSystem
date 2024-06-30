package com.ushirikeduc.app.kfka.Deserializers;

import Dto.ClassRoomEventEvent;
import Dto.HomeWorkStatusEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;


public class HomeWorkStatusDeserializer implements Deserializer<HomeWorkStatusEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public HomeWorkStatusEvent deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return  null ;
            }
            System.out.println("Deserializing ...");
            return  objectMapper.readValue(new String(data , StandardCharsets.UTF_8), HomeWorkStatusEvent.class);

        } catch (Exception e) {
            throw new SerializationException("Error When deserializing byte[] to ClassworkEvent", e);
        }


    }

}