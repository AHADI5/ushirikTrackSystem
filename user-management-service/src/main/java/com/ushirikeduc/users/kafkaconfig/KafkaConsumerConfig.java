package com.ushirikeduc.users.kafkaconfig;
import Dto.ParentEvent;
import Dto.TeacherEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private  String boostrapServers;

    /*
    *
    * Consuming Student event
    * */
    public Map<String , Object> consumerConfigParent() {
        return getStringObjectMap();
    }

    private Map<String, Object> getStringObjectMap() {
        HashMap<String , Object> propsParent = new HashMap<>();
        propsParent.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsParent.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsParent.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        propsParent.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        propsParent.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return propsParent ;
    }

    @Bean
    public ConsumerFactory<String , ParentEvent> consumerFactoryParent() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigParent(),
                new StringDeserializer(),
                new JsonDeserializer<>(ParentEvent.class)
        );

    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,ParentEvent> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String , ParentEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryParent());
        return factory;
    }

    /*
    * Consuming Teacher Event
    *
    * */

    public Map<String , Object> consumerConfigTeacher() {
        return getStringObjectMap();
    }

    @Bean
    public ConsumerFactory<String , TeacherEvent> consumerFactoryTeacher() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigTeacher(),
                new StringDeserializer(),
                new JsonDeserializer<>(TeacherEvent.class)
        );

    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,TeacherEvent> kafkaListenerContainerFactoryTeacher(){
        ConcurrentKafkaListenerContainerFactory<String , TeacherEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryTeacher());
        return factory;
    }

    /*
     * Consuming Course Event
     */
//    public Map<String , Object> consumerConfigCourse() {
//        return getStringObjectMap();
//    }
//
//    @Bean
//    public ConsumerFactory<String , CourseEvent> consumerFactoryCourse() {
//        return new DefaultKafkaConsumerFactory<>(
//                consumerConfigCourse(),
//                new StringDeserializer(),
//                new JsonDeserializer<>(CourseEvent.class)
//        );
//
//    }
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String ,CourseEvent> kafkaListenerContainerFactoryCourse(){
//        ConcurrentKafkaListenerContainerFactory<String , CourseEvent> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactoryCourse());
//        return factory;
//    }

}
