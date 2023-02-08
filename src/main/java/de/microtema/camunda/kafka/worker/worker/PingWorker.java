package de.microtema.camunda.kafka.worker.worker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Component
public class PingWorker {

    private final static Logger log = LoggerFactory.getLogger(PingWorker.class);

    private final NewTopic kafkaTopic;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public PingWorker(NewTopic kafkaTopic, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTopic = kafkaTopic;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @JobWorker(type = "ping.x")
    public Map<String, Object> execute(ActivatedJob job, JobClient client) throws JsonProcessingException {

        String correlationId = "123456789";//  UUID.randomUUID().toString();

        Map<String, Object> variables = job.getVariablesAsMap();
        variables.put("correlationId", correlationId);

        sendRecordToKafka(variables);

        return Collections.singletonMap("key", correlationId);
    }

    private void sendRecordToKafka(Map<String, Object> variables) throws JsonProcessingException {

        String dataAsJson = objectMapper.writeValueAsString(variables);

        kafkaTemplate.send("pong", dataAsJson).addCallback(this::successCallback, this::errorCallback);
    }

    private void successCallback(SendResult<?, ?> it) {

        log.info("Produced record: " + it.getRecordMetadata());
    }

    private void errorCallback(Throwable throwable) {

        log.error("Failed to produce to kafka", throwable);
    }

}
