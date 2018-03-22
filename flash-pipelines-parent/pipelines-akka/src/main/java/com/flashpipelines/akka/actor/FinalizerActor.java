package com.flashpipelines.akka.actor;

import akka.actor.AbstractActor;
import com.flashpipelines.core.Envelope;
import com.flashpipelines.core.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class FinalizerActor extends AbstractActor {

    private final Service<Envelope, Envelope> service;

    public FinalizerActor(Service<Envelope, Envelope> service) {
        this.service = service;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(Envelope.class, service::apply)
            .build();
    }
}
