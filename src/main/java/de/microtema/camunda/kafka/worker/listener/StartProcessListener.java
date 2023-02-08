package de.microtema.camunda.kafka.worker.listener;

import de.microtema.camunda.kafka.worker.worker.ValidateRequestWorker;
import io.camunda.zeebe.client.ZeebeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class StartProcessListener {

    private long count = 0l;

    private final static Logger log = LoggerFactory.getLogger(ValidateRequestWorker.class);

    private final ZeebeClient zeebeClient;

    public StartProcessListener(ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

    @Scheduled(fixedDelay = 1000)
    public void newCreateInstanceCommand() {

        zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId("ping-pong")
                .latestVersion()
                .variables(Map.of(
                        "name", "pong",
                        "key", UUID.randomUUID().toString(),
                        "payload", Map.of("ID", UUID.randomUUID().toString())))
                .send();
    }
}
