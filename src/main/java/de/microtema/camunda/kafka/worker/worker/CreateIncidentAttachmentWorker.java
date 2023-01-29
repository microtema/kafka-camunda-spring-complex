package de.microtema.camunda.kafka.worker.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Component
public class CreateIncidentAttachmentWorker {

    @JobWorker(type = "dx.order_assignmnet.create-atachment")
    public Map<String, Object> execute(ActivatedJob job) {

        String attachmentId = UUID.randomUUID().toString();

        Map<String, Object> variables = job.getVariablesAsMap();
        variables.put("attachmentId", attachmentId);

        return Collections.singletonMap("attachment", attachmentId);
    }
}
