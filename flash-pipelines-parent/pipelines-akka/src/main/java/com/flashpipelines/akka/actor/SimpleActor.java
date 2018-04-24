package com.flashpipelines.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.flashpipelines.core.Envelope;
import com.flashpipelines.core.Service;

/**
 * Actor type, which is processing message of type {@link Envelope} and send result to the next actor in pipeline.
 */
public final class SimpleActor extends AbstractActor {

    private final Service<Envelope, Envelope> service;
    private final ActorRef sendTo;

    private SimpleActor(Service<Envelope, Envelope> service, ActorRef sendTo) {
        this.service = service;
        this.sendTo = sendTo;
    }

    /**
     * Static factory for actor's {@link Props}.
     *
     * @param service processing service
     * @param sendTo the next actor in the pipeline
     * @return new {@link Props} instance.
     */
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
