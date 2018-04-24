package com.flashpipelines.example;

import akka.actor.ActorRef;
import com.flashpipelines.core.envelope.DummyEnvelope;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.stream.IntStream;

/**
 * Application.
 */
@SpringBootApplication
public class Application {

    /**
     * Application entry point.
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        ActorRef supervisor = (ActorRef) context.getBean("pipelineRouter");

        IntStream.range(0, 1).forEach(i -> supervisor.tell(new DummyEnvelope(), null));
    }
}
