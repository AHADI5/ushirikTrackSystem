package com.ushirikeduc.schools.config;

import Dto.ParentEvent;
import Dto.RuleEvent;
import Dto.StudentEvent;


import com.ushirikeduc.schools.config.Serializers.RuleSerializer;
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
    public Map<String , Object> producerConfigRule() {
        HashMap<String , Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, RuleSerializer.class);
        return props ;
    }
    @Bean
    public ProducerFactory<String , RuleEvent> producerFactoryRule() {
        return new DefaultKafkaProducerFactory<>(producerConfigRule());

    }
    @Bean
    public KafkaTemplate<String , RuleEvent> kafkaTemplateRule(
    ) {
        return  new KafkaTemplate<>(producerFactoryRule());
    }

}
