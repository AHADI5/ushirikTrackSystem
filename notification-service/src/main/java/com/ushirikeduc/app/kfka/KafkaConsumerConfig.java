package com.ushirikeduc.app.kfka;

import Dto.*;
import com.ushirikeduc.app.kfka.Deserializers.*;

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


/*
* Consuming Discipline event for discipline notifications
*
* */
    private ConsumerFactory<String, DisciplineEvent> consumerFactoryDiscipline() {
        Map<String, Object> propsStudent = new HashMap<>();
        propsStudent.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsStudent.put(ConsumerConfig.GROUP_ID_CONFIG, "discipline");
        propsStudent.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Use ErrorHandlingDeserializer for value deserializer
        ErrorHandlingDeserializer<DisciplineEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new DisciplineDeserializer());

        return new DefaultKafkaConsumerFactory<>(propsStudent, new StringDeserializer(), errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,DisciplineEvent> kafkaListenerContainerFactoryDiscipline(){
        ConcurrentKafkaListenerContainerFactory<String , DisciplineEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryDiscipline());
        return factory;
    }
/*
*Consuming ClassWorkEvent
* */
    private ConsumerFactory<String, ClassRoomEventEvent> consumerFactoryClassRoomEvent() {
        Map<String, Object> propsClassRoomEvent = new HashMap<>();
        propsClassRoomEvent.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsClassRoomEvent.put(ConsumerConfig.GROUP_ID_CONFIG, "classroom-Event");
        propsClassRoomEvent.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Use ErrorHandlingDeserializer for value deserializer
        ErrorHandlingDeserializer<ClassRoomEventEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new ClassRoomEventDeserializer());

        return new DefaultKafkaConsumerFactory<>(propsClassRoomEvent, new StringDeserializer(), errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,ClassRoomEventEvent> kafkaListenerContainerFactoryClassWorkEvent(){
        ConcurrentKafkaListenerContainerFactory<String , ClassRoomEventEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryClassRoomEvent());
        return factory;
    }

    /*
    * Consuming School Communique
    *
    * */
    private ConsumerFactory<String, SchoolCommuniqueEvent> schoolCommuniqueEventConsumerFactory() {
        Map<String, Object> propsClassRoomEvent = new HashMap<>();
        propsClassRoomEvent.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsClassRoomEvent.put(ConsumerConfig.GROUP_ID_CONFIG, "communique");
        propsClassRoomEvent.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Use ErrorHandlingDeserializer for value deserializer
        ErrorHandlingDeserializer<SchoolCommuniqueEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new SchoolCommuniqueDeserializer());

        return new DefaultKafkaConsumerFactory<>(propsClassRoomEvent, new StringDeserializer(), errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,SchoolCommuniqueEvent> kafkaListenerContainerFactoryCommunique(){
        ConcurrentKafkaListenerContainerFactory<String , SchoolCommuniqueEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(schoolCommuniqueEventConsumerFactory());
        return factory;
    }

    /**
     * Consuming recipient with uniqeDeviceKey
     * */
    private ConsumerFactory<String, RecepientInfoEvent> recepientInfoEventConsumerFactory() {
        Map<String, Object> propsClassRoomEvent = new HashMap<>();
        propsClassRoomEvent.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsClassRoomEvent.put(ConsumerConfig.GROUP_ID_CONFIG, "unique-device-key");
        propsClassRoomEvent.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Use ErrorHandlingDeserializer for value deserializer
        ErrorHandlingDeserializer<RecepientInfoEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new RecipientDeserializer());

        return new DefaultKafkaConsumerFactory<>(propsClassRoomEvent, new StringDeserializer(), errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,RecepientInfoEvent> kafkaListenerContainerFactoryRecipient(){
        ConcurrentKafkaListenerContainerFactory<String , RecepientInfoEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(recepientInfoEventConsumerFactory());
        return factory;
    }



    /**
     * Consuming maxEvent
     * */
    private ConsumerFactory<String, MaxEvent> maxEventConsumerFactory() {
        Map<String, Object> propsClassRoomEvent = new HashMap<>();
        propsClassRoomEvent.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsClassRoomEvent.put(ConsumerConfig.GROUP_ID_CONFIG, "max-event");
        propsClassRoomEvent.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Use ErrorHandlingDeserializer for value deserializer
        ErrorHandlingDeserializer<MaxEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new MaxEventDeserializer());

        return new DefaultKafkaConsumerFactory<>(propsClassRoomEvent, new StringDeserializer(), errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,MaxEvent> kafkaListenerContainerFactoryMaxEvent(){
        ConcurrentKafkaListenerContainerFactory<String , MaxEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(maxEventConsumerFactory());
        return factory;
    }

    /*
    Consuming homewework event
    *
    **/

    private ConsumerFactory<String, HomeWorkAssignedEvent> homeWorkEventConsumerFactory() {
        Map<String, Object> propsClassRoomEvent = new HashMap<>();
        propsClassRoomEvent.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsClassRoomEvent.put(ConsumerConfig.GROUP_ID_CONFIG, "homework-event");
        propsClassRoomEvent.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Use ErrorHandlingDeserializer for value deserializer
        ErrorHandlingDeserializer<HomeWorkAssignedEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new HomeWorkDeserializer());

        return new DefaultKafkaConsumerFactory<>(propsClassRoomEvent, new StringDeserializer(), errorHandlingDeserializer);
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,HomeWorkAssignedEvent> kafkaListenerContainerFactoryHomeWork(){
        ConcurrentKafkaListenerContainerFactory<String , HomeWorkAssignedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(homeWorkEventConsumerFactory());
        return factory;
    }


        /*
    Consuming homeweworkStatus event
    *
    **/

    private ConsumerFactory<String, HomeWorkStatusEvent> homeWorkStatusEventConsumerFactory() {
        Map<String, Object> propsClassRoomEvent = new HashMap<>();
        propsClassRoomEvent.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsClassRoomEvent.put(ConsumerConfig.GROUP_ID_CONFIG, "homework-status-event");
        propsClassRoomEvent.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Use ErrorHandlingDeserializer for value deserializer
        ErrorHandlingDeserializer<HomeWorkStatusEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new HomeWorkStatusDeserializer());

        return new DefaultKafkaConsumerFactory<>(propsClassRoomEvent, new StringDeserializer(), errorHandlingDeserializer);
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,HomeWorkStatusEvent> kafkaListenerContainerFactoryHomeWorkStatus(){
        ConcurrentKafkaListenerContainerFactory<String , HomeWorkStatusEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(homeWorkStatusEventConsumerFactory());
        return factory;
    }
}
