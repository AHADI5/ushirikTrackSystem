package com.ushirikeduc.courseservice.kafka;

import Dto.ClassWorkEvent;
import Dto.CourseEvent;
import Dto.StudentEvent;
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

    /*
    * Producing course
    * */
    public Map<String , Object> producerConfigCourse() {
        HashMap<String , Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props ;
    }
    @Bean
    public ProducerFactory<String , CourseEvent> producerFactoryCourse() {
        return new DefaultKafkaProducerFactory<>(producerConfigCourse());

    }
    @Bean
    public KafkaTemplate<String , CourseEvent> kafkaTemplateCourse(
    ) {
        return  new KafkaTemplate<>(producerFactoryCourse());
    }


    /*
     * Producing classWork
     * */
    public Map<String , Object> producerConfigClasswork() {
        HashMap<String , Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props ;
    }
    @Bean
    public ProducerFactory<String , ClassWorkEvent> producerFactoryClasswork() {
        return new DefaultKafkaProducerFactory<>(producerConfigClasswork());

    }
    @Bean
    public KafkaTemplate<String , ClassWorkEvent> kafkaTemplateClasswork(
    ) {
        return  new KafkaTemplate<>(producerFactoryClasswork());
    }
}
