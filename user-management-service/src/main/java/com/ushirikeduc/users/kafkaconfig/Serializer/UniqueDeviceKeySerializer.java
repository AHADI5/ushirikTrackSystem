package com.ushirikeduc.users.kafkaconfig.Serializer;

import Dto.RecepientInfoEvent;
import Dto.SchoolCommuniqueEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class UniqueDeviceKeySerializer implements Serializer<RecepientInfoEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, RecepientInfoEvent data) {
//        return new byte[0];
        try {
            if (data == null) {
                System.out.println("Null received at serializing phase");
                return null ;
            }
            System.out.println("Serializing ...");
            return objectMapper.writeValueAsBytes(data);

        } catch (Exception e) {
            throw new SerializationException("Error when Serializing Recipient to byte");

        }
    }
}
