package com.flashpipelines.core.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.pattern.PatternsCS;
import com.flashpipelines.core.AsyncService;
import com.flashpipelines.core.Envelope;

public class AsyncActor extends AbstractActor {

    private final AsyncService service;
    private final ActorRef sendTo;

    public AsyncActor(AsyncService service, ActorRef sendTo) {
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
        PatternsCS.pipe(service.apply(envelope), getContext().dispatcher())
            .to(sendTo, getSelf());
    }
}
