package com.flashpipelines.core.actor;

import akka.actor.AbstractActor;
import com.flashpipelines.core.Envelope;
import com.flashpipelines.core.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class FinalizerActor extends AbstractActor {

    private final Service service;

    public FinalizerActor(Service service) {
        this.service = service;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(Envelope.class, envelope -> {
                service.apply(envelope);

                envelope.completeFuture();
            })
            .build();
    }
}
