package com.ushirikeduc.classservice.kafka;

import Dto.ClassRoomEvent;
import Dto.ParentEvent;
import Dto.StudentEvent;
import com.ushirikeduc.classservice.kafka.Serializer.ClassRoomSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;


//this is a producer configuration, and it will be used all over others microservices
@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private  String boostrapServers;

    /***
     *
     * producing Student Event
     */
    public Map<String , Object> producerConfigClassRoom() {
        HashMap<String , Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ClassRoomSerializer.class);
        return props ;
    }
    @Bean
    public ProducerFactory<String , ClassRoomEvent> producerFactoryClassRoom() {
        return new DefaultKafkaProducerFactory<>(producerConfigClassRoom());

    }
    @Bean
    public KafkaTemplate<String , ClassRoomEvent> kafkaTemplateClassRoom(
    ) {
        return  new KafkaTemplate<>(producerFactoryClassRoom());
    }




}
