package org.example;

import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import org.springframework.stereotype.Component;

@Component
public class FinishingServiceTask {

    @ZeebeWorker(type="javaDelegateExample", autoComplete = true)
    public void serve(){
        System.out.println("+++++++++Finishing the flow ++++++++++++");
    }
}
