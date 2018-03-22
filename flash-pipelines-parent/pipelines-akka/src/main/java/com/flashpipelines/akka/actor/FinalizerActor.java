package com.flashpipelines.akka.actor;

import akka.actor.AbstractActor;
import com.flashpipelines.akka.Envelope;
import com.flashpipelines.akka.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class FinalizerActor extends AbstractActor {

    private final Service service;

    public FinalizerActor(Service service) {
        this.service = service;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(Envelope.class, service::apply)
            .build();
    }
}
