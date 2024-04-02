package com.ushirikeduc.student.config;

import Dto.ParentEvent;
import Dto.StudentEvent;
import com.ushirikeduc.student.config.Serializers.ParentSerializer;
import com.ushirikeduc.student.config.Serializers.StudentSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;


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
    public Map<String , Object> producerConfigStudent() {
        HashMap<String , Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StudentSerializer.class);
        return props ;
    }
    @Bean
    public ProducerFactory<String , StudentEvent> producerFactoryStudent() {
        return new DefaultKafkaProducerFactory<>(producerConfigStudent());

    }
    @Bean
    public KafkaTemplate<String , StudentEvent> kafkaTemplateStudent(
    ) {
        return  new KafkaTemplate<>(producerFactoryStudent());
    }

    /***
     *
     * producing Parent Event
     */

    public Map<String , Object> producerConfigParent() {
        HashMap<String , Object> propsParent = new HashMap<>();
        propsParent.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsParent.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        propsParent.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ParentSerializer.class);
        return propsParent ;
    }
    @Bean
    public ProducerFactory<String , ParentEvent> producerFactoryParent() {
        return new DefaultKafkaProducerFactory<>(producerConfigParent());

    }
    @Bean
    public KafkaTemplate<String , ParentEvent> kafkaTemplateParent(
    ) {
        return  new KafkaTemplate<>(producerFactoryParent());
    }
}
