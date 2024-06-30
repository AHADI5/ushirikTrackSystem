package com.ushirikeduc.maxmanagementservice.kafka.Producer;

import Dto.DirectorEvent;
import Dto.MaxEvent;

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
     * producing max Event
     */
    public Map<String, Object> producerConfigMax() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MaxSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, MaxEvent> producerFactoryMax() {
        return new DefaultKafkaProducerFactory<>(producerConfigMax());

    }

    @Bean
    public KafkaTemplate<String, MaxEvent> kafkaTemplateMax(
    ) {
        return new KafkaTemplate<>(producerFactoryMax());
    }



}

