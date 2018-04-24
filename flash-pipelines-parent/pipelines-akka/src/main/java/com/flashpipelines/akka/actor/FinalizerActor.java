package com.flashpipelines.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.flashpipelines.core.Envelope;
import com.flashpipelines.core.Service;

/**
 * Actor type, which should be invoked last in the actor's pipeline.
 */
public final class FinalizerActor extends AbstractActor {

    private final Service<Envelope, Envelope> service;

    private FinalizerActor(Service<Envelope, Envelope> service) {
        this.service = service;
    }

    /**
     * Static factory for actor's {@link Props}.
     *
     * @param service async processing service
     * @return new {@link Props} instance.
     */
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
