package com.ushirikeduc.classservice.kafka;

import Dto.ClassWorkEvent;
import Dto.CourseEvent;
import Dto.StudentEvent;
import Dto.TeacherEvent;
import com.ushirikeduc.classservice.kafka.Deserializers.ClassWorkDeserializer;
import com.ushirikeduc.classservice.kafka.Deserializers.CourseDeserializer;
import com.ushirikeduc.classservice.kafka.Deserializers.StudentDeserializer;
import com.ushirikeduc.classservice.kafka.Deserializers.TeacherDeserializer;
import com.ushirikeduc.classservice.model.Teacher;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

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


    private ConsumerFactory<String, StudentEvent> consumerFactoryStudent() {
        Map<String , Object> propsStudent = new HashMap<>();
        propsStudent.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsStudent.put(ConsumerConfig.GROUP_ID_CONFIG, "student-classroom");
        propsStudent.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

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
    * Consuming Teacher Event
    *
    * */

    private ConsumerFactory<String, TeacherEvent> consumerFactoryTeacher() {
        Map<String , Object> propsTeacher = new HashMap<>();
        propsTeacher.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsTeacher.put(ConsumerConfig.GROUP_ID_CONFIG, "teacher-classroom");
        propsTeacher.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        ErrorHandlingDeserializer<TeacherEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new TeacherDeserializer());

        return new DefaultKafkaConsumerFactory<>(propsTeacher, new StringDeserializer(), errorHandlingDeserializer);
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
    private ConsumerFactory<String, CourseEvent> consumerFactoryCourse() {
        Map<String , Object> propsCourse = new HashMap<>();
        propsCourse.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsCourse.put(ConsumerConfig.GROUP_ID_CONFIG, "course-classroom");
        propsCourse.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        ErrorHandlingDeserializer<CourseEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new CourseDeserializer());

        return new DefaultKafkaConsumerFactory<>(propsCourse, new StringDeserializer(), errorHandlingDeserializer);
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,CourseEvent> kafkaListenerContainerFactoryCourse(){
        ConcurrentKafkaListenerContainerFactory<String , CourseEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryCourse());
        return factory;
    }

    /*
    * Consuming classwork Event
    * */

    private ConsumerFactory<String, ClassWorkEvent> consumerFactoryClassWork() {
        Map<String , Object> propsCourse = new HashMap<>();
        propsCourse.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsCourse.put(ConsumerConfig.GROUP_ID_CONFIG, "classwork-classroom");
        propsCourse.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        ErrorHandlingDeserializer<ClassWorkEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new ClassWorkDeserializer());

        return new DefaultKafkaConsumerFactory<>(propsCourse, new StringDeserializer(), errorHandlingDeserializer);
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,ClassWorkEvent> kafkaListenerContainerFactoryClassWork(){
        ConcurrentKafkaListenerContainerFactory<String , ClassWorkEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryClassWork());
        return factory;
    }

}
