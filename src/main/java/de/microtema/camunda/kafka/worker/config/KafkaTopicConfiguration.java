package de.microtema.camunda.kafka.worker.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {

    public static final String VALIDATE_TOPIC_NAME = "dx.order_assignmnet.validate";

    @Bean
    public NewTopic kafkaTopic() {

        return new NewTopic(VALIDATE_TOPIC_NAME, 3, (short) 3);
    }

}
