package com.ushirikeduc.classservice.kafka;

import Dto.*;
import com.ushirikeduc.classservice.kafka.Serializer.ClassRoomSerializer;
import com.ushirikeduc.classservice.kafka.Serializer.EventSerializer;
import com.ushirikeduc.classservice.model.HomeWorkAssigned;
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


    /*
    * Producing Events
    *
    * */

    public Map<String , Object> producerConfigClassRoomEvent() {
        HashMap<String , Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EventSerializer.class);
        return props ;
    }
    @Bean
    public ProducerFactory<String , ClassRoomEventEvent> producerFactoryClassRoomEvent() {
        return new DefaultKafkaProducerFactory<>(producerConfigClassRoomEvent());

    }
    @Bean
    public KafkaTemplate<String , ClassRoomEventEvent> kafkaTemplateClassRoomEvent(
    ) {
        return  new KafkaTemplate<>(producerFactoryClassRoomEvent());
    }


/*

*Consuming homework Assigned
**/
public Map<String , Object> producerConfigHomeWork() {
    HashMap<String , Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, HomeWorkAssignedEvent.class);
    return props ;
}
    @Bean
    public ProducerFactory<String , HomeWorkAssignedEvent> producerFactoryHomeWork() {
        return new DefaultKafkaProducerFactory<>(producerConfigHomeWork());

    }
    @Bean
    public KafkaTemplate<String , HomeWorkAssignedEvent> kafkaTemplateHomeWork(
    ) {
        return  new KafkaTemplate<>(producerFactoryHomeWork());
    }


}
