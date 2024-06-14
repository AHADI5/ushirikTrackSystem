package com.ushirikeduc.disciplineservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


//Topic configuration : this will be used all over the system

@Configuration
public class KafkaTopicConfig {


    @Bean
    public NewTopic disciplineTopic() {
        return TopicBuilder.name("discipline-event-created")
                .build();
    }




}
