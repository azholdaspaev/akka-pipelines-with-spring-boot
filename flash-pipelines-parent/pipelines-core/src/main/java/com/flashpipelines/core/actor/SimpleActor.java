package com.flashpipelines.core.actor;

import akka.actor.AbstractActor;
import akka.routing.Router;
import com.flashpipelines.core.Envelope;
import com.flashpipelines.core.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class SimpleActor extends AbstractActor {

    private final Service service;
    private final Router nextRoute;

    public SimpleActor(Service service, Router nextRoute) {
        this.service = service;
        this.nextRoute = nextRoute;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(Envelope.class, this::apply)
            .build();
    }

    private void apply(Envelope envelope) {
        nextRoute.route(service.apply(envelope), getSelf());
    }
}
