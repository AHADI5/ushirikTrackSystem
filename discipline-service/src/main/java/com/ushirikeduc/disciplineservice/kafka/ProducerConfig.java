package com.ushirikeduc.disciplineservice.kafka;

import Dto.ClassWorkEvent;

import Dto.DisciplineEvent;
import Dto.HomeWorkStatusEvent;
import com.ushirikeduc.disciplineservice.kafka.Serializers.DisciplineSerializer;
import com.ushirikeduc.disciplineservice.kafka.Serializers.HomeWorkStatusSerializer;
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
    public Map<String , Object> producerFactory() {
        HashMap<String , Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DisciplineSerializer.class);
        return props;

    }

    @Bean
    public ProducerFactory<String , DisciplineEvent> producerFactoryDiscipline() {
        return  new DefaultKafkaProducerFactory<>(producerFactory());
    }
    @Bean
    public KafkaTemplate<String , DisciplineEvent> kafkaTemplate() {
        return  new KafkaTemplate<>(producerFactoryDiscipline());
    }

    /**
     * Producing HomeWork Status
     * **/

    public Map<String , Object> homeWorkStatusProducerFactory() {
        HashMap<String , Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, HomeWorkStatusSerializer.class);
        return props;

    }

    @Bean
    public ProducerFactory<String , HomeWorkStatusEvent> producerFactoryHomeWorkStatus() {
        return  new DefaultKafkaProducerFactory<>(homeWorkStatusProducerFactory());
    }
    @Bean
    public KafkaTemplate<String , HomeWorkStatusEvent> kafkaTemplateHomeWorkStatus() {
        return  new KafkaTemplate<>(producerFactoryHomeWorkStatus());
    }
}
