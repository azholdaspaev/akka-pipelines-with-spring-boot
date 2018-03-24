package com.flashpipelines.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.flashpipelines.core.Envelope;
import com.flashpipelines.core.Service;

public class FinalizerActor extends AbstractActor {

    private final Service<Envelope, Envelope> service;

    private FinalizerActor(Service<Envelope, Envelope> service) {
        this.service = service;
    }

    public static Props props(Service<Envelope, Envelope> service) {
        return Props.create(FinalizerActor.class, service);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(Envelope.class, service::apply)
            .build();
    }
}
