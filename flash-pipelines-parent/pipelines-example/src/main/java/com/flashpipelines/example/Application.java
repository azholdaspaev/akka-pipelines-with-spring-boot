package com.flashpipelines.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.flashpipelines.core.Envelope;
import com.flashpipelines.core.boot.SpringExtension;
import com.flashpipelines.core.pipeline.ActorReferencePipeline;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.stream.IntStream;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        ActorSystem system = context.getBean(ActorSystem.class);
        SpringExtension ext = context.getBean(SpringExtension.class);


        ActorReferencePipeline pipeline = context.getBean(ActorReferencePipeline.class);

        ActorRef supervisor = system.actorOf(ext.props("pipelineSupervisorActor", pipeline, ext));

        IntStream.range(0, 1).forEach(i -> supervisor.tell(new Envelope(), null));
    }
}
