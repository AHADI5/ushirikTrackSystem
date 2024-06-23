package com.ushirikeduc.schools.config;

import Dto.DirectorEvent;
import Dto.ParentEvent;
import Dto.SchoolCommuniqueEvent;
import Dto.StudentEvent;
import com.ushirikeduc.schools.config.Serializers.CommuniqueSerializer;
import com.ushirikeduc.schools.config.Serializers.DirectorSerializer;

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
    private String boostrapServers;

    /***
     *
     * producing Student Event
     */
    public Map<String, Object> producerConfigDirector() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DirectorSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, DirectorEvent> producerFactoryDirector() {
        return new DefaultKafkaProducerFactory<>(producerConfigDirector());

    }

    @Bean
    public KafkaTemplate<String, DirectorEvent> kafkaTemplateStudent(
    ) {
        return new KafkaTemplate<>(producerFactoryDirector());
    }

    /*
    * Producing communique event
    * */
    public Map<String, Object> producerConfigCommunique() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CommuniqueSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, SchoolCommuniqueEvent> producerFactoryCommunique() {
        return new DefaultKafkaProducerFactory<>(producerConfigCommunique());

    }

    @Bean
    public KafkaTemplate<String,SchoolCommuniqueEvent> kafkaTemplateCommunique(
    ) {
        return new KafkaTemplate<>(producerFactoryCommunique());
    }


}

