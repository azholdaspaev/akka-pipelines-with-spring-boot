package com.flashpipelines.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.pattern.PatternsCS;
import com.flashpipelines.core.Envelope;
import com.flashpipelines.core.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Asynchronous actor type.
 */
public final class AsyncActor extends AbstractActor {

    private final Service<Envelope, CompletableFuture<Envelope>> service;
    private final ActorRef sendTo;

    private AsyncActor(Service<Envelope, CompletableFuture<Envelope>> service, ActorRef sendTo) {
        this.service = service;
        this.sendTo = sendTo;
    }

    /**
     * Static factory for actor's {@link Props}.
     *
     * @param service async processing service
     * @param sendTo the next actor in the pipeline
     * @return new {@link Props} instance.
     */
    public static Props props(Service<Envelope, CompletableFuture<Envelope>> service, ActorRef sendTo) {
        return Props.create(AsyncActor.class, service, sendTo);
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
