package com.ushirikeduc.student.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
@Configuration
public class kafkaConfiguration {
    @Value("create-student")
    private String studentTopic ;
    @Value("create-parent")
    private String parentTopic ;

    //Spring bran for kafka topic
    @Bean
    public NewTopic studentTopic() {
        return TopicBuilder.name(studentTopic)
                .build();

    }
    @Bean
    public NewTopic parentTopic() {
        return TopicBuilder.name(parentTopic)
                .build();

    }

}
