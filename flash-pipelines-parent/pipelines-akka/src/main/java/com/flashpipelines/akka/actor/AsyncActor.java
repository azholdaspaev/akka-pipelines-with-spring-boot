package com.flashpipelines.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.pattern.PatternsCS;
import com.flashpipelines.akka.AsyncService;
import com.flashpipelines.akka.Envelope;

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
