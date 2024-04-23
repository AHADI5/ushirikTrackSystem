package com.ushirikeduc.classservice.kafkacofing;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
@Configuration
public class KafkaConfig {
    @Value("create-classroom")
    private String classRoomTopic;

    //Spring bean for kafka topic
    @Bean
    public NewTopic classRoomTopic() {
        return TopicBuilder.name(classRoomTopic)
                .build();
    }
}
