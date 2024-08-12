package org.example;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeDeployment;
import io.camunda.zeebe.spring.client.config.ZeebeClientStarterAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableZeebeClient
@Import(ZeebeClientStarterAutoConfiguration.class)
@ZeebeDeployment(resources = "classpath:event-msg-demo.bpmn")
public class Main implements CommandLineRunner {

    @Autowired
    private ZeebeClient client;

    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
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