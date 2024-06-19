package com.ushirikeduc.courseservice.kafka;

import Dto.ClassWorkEvent;
import Dto.CourseEvent;
import Dto.HomeWorkEvent;
import com.ushirikeduc.courseservice.kafka.Serializers.ClassWorkSerializer;
import com.ushirikeduc.courseservice.kafka.Serializers.CourseSerializer;
import com.ushirikeduc.courseservice.kafka.Serializers.HomeWorkSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class ProducerConfig {
    public Map<String , Object> producerConfigClassWork() {
        HashMap<String , Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ClassWorkSerializer.class);
        return props;

    }

    @Bean
    public ProducerFactory<String , ClassWorkEvent> producerFactoryClassWork() {
        return  new DefaultKafkaProducerFactory<>(producerConfigClassWork());
    }
    @Bean
    public KafkaTemplate<String , ClassWorkEvent> kafkaTemplateClassWork() {
        return  new KafkaTemplate<>(producerFactoryClassWork());
    }

    //todo publish courseEvent


    public Map<String , Object> producerFactoryCourses() {
        HashMap<String , Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CourseSerializer.class);
        return props;

    }

    @Bean
    public ProducerFactory<String , CourseEvent> producerFactoryCourse() {
        return  new DefaultKafkaProducerFactory<>(producerFactoryCourses());
    }
    @Bean
    public KafkaTemplate<String , CourseEvent> kafkaTemplateCourse() {
        return  new KafkaTemplate<>(producerFactoryCourse());
    }
    //todo publish homeworkEvent

    public Map<String , Object> producerFactoryHomeWorks() {
        HashMap<String , Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, HomeWorkSerializer.class);
        return props;

    }

    @Bean
    public ProducerFactory<String , HomeWorkEvent> producerFactoryHomeWork() {
        return  new DefaultKafkaProducerFactory<>(producerFactoryHomeWorks());
    }
    @Bean
    public KafkaTemplate<String , HomeWorkEvent> kafkaTemplateHomeWork() {
        return  new KafkaTemplate<>(producerFactoryHomeWork());
    }
}
