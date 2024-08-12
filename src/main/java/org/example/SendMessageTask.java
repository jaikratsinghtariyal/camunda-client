package org.example;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SendMessageTask {

    @Autowired
    private ZeebeClient client;

    @ZeebeWorker(type="sendMessageTask", autoComplete = true)
    public void serve(){

        System.out.println("+++++++++Starting the flow ++++++++++++");

        String key = "dummy";
        Map<String, String> map = new HashMap<>();
        map.put("name", "Jaikrat");
        map.put("age", "20");

        client.newPublishMessageCommand().messageName("some-key").correlationKey(key)
                .variables(map).send().exceptionally(throwable -> {
                    throw new RuntimeException("Could not publish message", throwable);
                });

        System.out.println("all done");
    }
}
