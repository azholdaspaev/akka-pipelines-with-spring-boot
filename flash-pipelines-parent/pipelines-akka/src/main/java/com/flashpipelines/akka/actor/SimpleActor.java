package com.flashpipelines.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.flashpipelines.core.Envelope;
import com.flashpipelines.core.Service;

public class SimpleActor extends AbstractActor {

    private final Service<Envelope, Envelope> service;
    private final ActorRef sendTo;

    private SimpleActor(Service<Envelope, Envelope> service, ActorRef sendTo) {
        this.service = service;
        this.sendTo = sendTo;
    }

    public static Props props(Service<Envelope, Envelope> service, ActorRef sendTo) {
        return Props.create(SimpleActor.class, service, sendTo);
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
