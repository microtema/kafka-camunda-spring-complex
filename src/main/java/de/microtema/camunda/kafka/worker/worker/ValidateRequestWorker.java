package de.microtema.camunda.kafka.worker.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.microtema.camunda.kafka.worker.config.KafkaTopicConfiguration;
import io.camunda.zeebe.client.ZeebeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ValidateRequestWorker {

    private final static Logger log = LoggerFactory.getLogger(ValidateRequestWorker.class);

    private final ZeebeClient zeebeClient;
    private final ObjectMapper objectMapper;

    public ValidateRequestWorker(ZeebeClient zeebeClient, ObjectMapper objectMapper) {
        this.zeebeClient = zeebeClient;
        this.objectMapper = objectMapper;
    }

    @SuppressWarnings("unchecked")
    @KafkaListener(topics = KafkaTopicConfiguration.VALIDATE_TOPIC_NAME)
    public void processMessage(String content) throws Exception {
        log.info("Received record: " + content);

        Map<String, String> message = objectMapper.readValue(content, Map.class);

        String correlationId = message.get("correlationId");

        log.info("Correlate to process instance using correlationId: " + correlationId);

        zeebeClient.newPublishMessageCommand()
                .messageName("MsgKafkaRecordReceived")
                .correlationKey(correlationId)
                .send()
                .exceptionally(it -> {
                    throw new RuntimeException("Could not hand over record to Zeebe: " + content + ". check nested exception for details: " + it.getMessage());
                });
    }

}
