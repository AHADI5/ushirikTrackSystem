package com.ushirikeduc.users.kafkaconfig;


import Dto.RecepientInfoEvent;
import com.ushirikeduc.users.kafkaconfig.Serializer.UniqueDeviceKeySerializer;
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


    public Map<String, Object> producerConfigUniqueDeviceKey() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UniqueDeviceKeySerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, RecepientInfoEvent> producerFactoryUniqueDeviceKey() {
        return new DefaultKafkaProducerFactory<>(producerConfigUniqueDeviceKey());

    }

    @Bean
    public KafkaTemplate<String,RecepientInfoEvent> kafkaTemplateUniqueDeviceKey(
    ) {
        return new KafkaTemplate<>(producerFactoryUniqueDeviceKey());
    }


}

