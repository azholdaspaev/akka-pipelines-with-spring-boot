package com.flashpipelines.core.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.flashpipelines.core.Envelope;
import com.flashpipelines.core.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class SimpleActor extends AbstractActor {

    private final Service service;
    private final ActorRef sendTo;

    public SimpleActor(Service service, ActorRef sendTo) {
        this.service = service;
        this.sendTo = sendTo;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(Envelope.class, this::apply)
            .build();
    }

    private void apply(Envelope envelope) {
        sendTo.tell(service.apply(envelope), getSelf());
    }
}
