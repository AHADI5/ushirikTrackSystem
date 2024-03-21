package com.ushirikeduc.courseservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
@Configuration
public class kafkaConfiguration {
    @Value("create-course")
    private String courseTopic ;
    @Value("create-classwork")
    private String classWorkTopic ;


    //Spring bean for kafka topic
    @Bean
    public NewTopic courseTopic() {
        return TopicBuilder.name(courseTopic)
                .build();

    }
    @Bean
    public NewTopic classWorkTopic() {
        return TopicBuilder.name(classWorkTopic)
                .build();

    }



}
