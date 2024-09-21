package com.ushirikeduc.schools.config;

import Dto.ClassRoomEvent;
import Dto.ClassWorkEvent;

import Dto.DisciplineCommuniqueEvent;
import com.ushirikeduc.schools.config.Deserializers.ClassRoomDeserializer;
import com.ushirikeduc.schools.config.Deserializers.DisciplineCommuniqueDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private  String boostrapServers;




    private ConsumerFactory<String, ClassRoomEvent> consumerFactoryClassRoom() {
        Map<String, Object> propsClassRoom = new HashMap<>();
        propsClassRoom.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsClassRoom.put(ConsumerConfig.GROUP_ID_CONFIG, "classroom");
        propsClassRoom.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Use ErrorHandlingDeserializer for value deserializer
        ErrorHandlingDeserializer<ClassRoomEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new ClassRoomDeserializer());

        return new DefaultKafkaConsumerFactory<>(propsClassRoom, new StringDeserializer(), errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,ClassRoomEvent> kafkaListenerContainerFactoryClassRoom(){
        ConcurrentKafkaListenerContainerFactory<String , ClassRoomEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryClassRoom());
        return factory;
    }


    private ConsumerFactory<String, DisciplineCommuniqueEvent> consumerFactoryCommuniqueEvent() {
        Map<String, Object> propsClassRoom = new HashMap<>();
        propsClassRoom.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsClassRoom.put(ConsumerConfig.GROUP_ID_CONFIG, "discipline_communique");
        propsClassRoom.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Use ErrorHandlingDeserializer for value deserializer
        ErrorHandlingDeserializer<DisciplineCommuniqueEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new DisciplineCommuniqueDeserializer());

        return new DefaultKafkaConsumerFactory<>(propsClassRoom, new StringDeserializer(), errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,DisciplineCommuniqueEvent> kafkaListenerContainerFactoryDisciplineCom(){
        ConcurrentKafkaListenerContainerFactory<String , DisciplineCommuniqueEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryCommuniqueEvent());
        return factory;
    }


}
