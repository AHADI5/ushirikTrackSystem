package com.ushirikeduc.users.kafkaconfig;
import Dto.DirectorEvent;
import Dto.ParentEvent;
import Dto.TeacherEvent;
import com.ushirikeduc.users.kafkaconfig.Deserializers.DirectorDiserializer;
import com.ushirikeduc.users.kafkaconfig.Deserializers.ParentDeserializer;
import com.ushirikeduc.users.kafkaconfig.Deserializers.TeacherDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private  String boostrapServers;

    /*
    *
    * Consuming Parent event
    * */


    public ConsumerFactory<String, ParentEvent> consumerFactoryParent() {
        Map<String , Object> propsParent = new HashMap<>();
        propsParent.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsParent.put(ConsumerConfig.GROUP_ID_CONFIG, "parent-user");
        propsParent.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        propsParent.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsParent.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ParentDeserializer.class);


        return new DefaultKafkaConsumerFactory<>(propsParent , new StringDeserializer(), new ParentDeserializer()) ;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,ParentEvent> kafkaListenerContainerFactoryParent(){
        ConcurrentKafkaListenerContainerFactory<String , ParentEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryParent());
        return factory;
    }

    /*
    * Consuming Teacher Event
    *
    * */

    public ConsumerFactory<String, TeacherEvent> consumerFactoryTeacher() {
        Map<String , Object> propsTeacher = new HashMap<>();
        propsTeacher.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsTeacher.put(ConsumerConfig.GROUP_ID_CONFIG, "teacher-user");
        propsTeacher.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        propsTeacher.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsTeacher.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TeacherDeserializer.class);


        return new DefaultKafkaConsumerFactory<>(propsTeacher , new StringDeserializer(), new TeacherDeserializer()) ;
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,TeacherEvent> kafkaListenerContainerFactoryTeacher(){
        ConcurrentKafkaListenerContainerFactory<String , TeacherEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryTeacher());
        return factory;
    }

    /*
    * Consuming schoolDirector Event
    *
    * */

    public ConsumerFactory<String, DirectorEvent> consumerFactoryDirector() {
        Map<String , Object> propsDirector = new HashMap<>();
        propsDirector.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsDirector.put(ConsumerConfig.GROUP_ID_CONFIG, "director-user");
        propsDirector.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        propsDirector.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsDirector.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DirectorDiserializer.class);


        return new DefaultKafkaConsumerFactory<>(propsDirector , new StringDeserializer(), new DirectorDiserializer()) ;
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,DirectorEvent> kafkaListenerContainerFactoryDirector(){
        ConcurrentKafkaListenerContainerFactory<String , DirectorEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryDirector());
        return factory;
    }





}
