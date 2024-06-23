package com.ushirikeduc.app.kfka.Deserializers;

import Dto.RecepientInfoEvent;
import Dto.SchoolCommuniqueEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;


public class  RecipientDeserializer implements Deserializer<RecepientInfoEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public RecepientInfoEvent deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return  null ;
            }
            System.out.println("Deserializing ...");
            return  objectMapper.readValue(new String(data , StandardCharsets.UTF_8), RecepientInfoEvent.class);

        } catch (Exception e) {
            throw new SerializationException("Error When deserializing byte[] to Recipient Info", e);
        }


    }

}
