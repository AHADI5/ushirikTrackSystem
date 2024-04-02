package com.ushirikeduc.maxmanagementservice.kafka.Consumer;

import Dto.ClassWorkEvent;
import Dto.StudentEvent;
import com.ushirikeduc.maxmanagementservice.kafka.Consumer.Deserializers.ClassWorkDeserializer;
import com.ushirikeduc.maxmanagementservice.kafka.Consumer.Deserializers.StudentDeserializer;
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
    *
    * Consuming Student event
    * */
//    public Map<String , Object> consumerConfigStudent() {
//        return getStringObjectMap();
//    }
//
    /*
     *
     * Consuming Student event
     * */


    private ConsumerFactory<String, StudentEvent> consumerFactoryStudent() {
        Map<String, Object> propsStudent = new HashMap<>();
        propsStudent.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsStudent.put(ConsumerConfig.GROUP_ID_CONFIG, "student-max");
        propsStudent.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Use ErrorHandlingDeserializer for value deserializer
        ErrorHandlingDeserializer<StudentEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new StudentDeserializer());

        return new DefaultKafkaConsumerFactory<>(propsStudent, new StringDeserializer(), errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,StudentEvent> kafkaListenerContainerFactoryStudent(){
        ConcurrentKafkaListenerContainerFactory<String , StudentEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryStudent());
        return factory;
    }

    /*
     * Consuming Classwork Event
     *
     * */

    public ConsumerFactory<String, ClassWorkEvent> consumerFactoryClasswork(){
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "classwork-max");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ClassWorkDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new ClassWorkDeserializer());

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ClassWorkEvent> kafkaListenerContainerFactoryClasswork() {
        ConcurrentKafkaListenerContainerFactory<String, ClassWorkEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryClasswork());
        return factory;
    }


//    @Bean
//    public ConsumerFactory<String , ClassWorkEvent> consumerFactoryClasswork() {
//        return new DefaultKafkaConsumerFactory<>(
//                classWorkConsumer(),
//                new StringDeserializer(),
//                new ClassWorkDeserializer()
//        );
//
//    }
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String ,ClassWorkEvent> kafkaListenerContainerFactoryClasswork(){
//        ConcurrentKafkaListenerContainerFactory<String , ClassWorkEvent> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactoryClasswork());
//        return factory;
//    }

}
