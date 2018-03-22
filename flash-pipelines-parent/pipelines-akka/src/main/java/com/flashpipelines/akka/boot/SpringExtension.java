package com.flashpipelines.akka.boot;

import akka.actor.ActorRef;
import akka.actor.Extension;
import akka.actor.Props;
import com.flashpipelines.akka.actor.ActorReference;
import com.flashpipelines.akka.actor.ActorType;
import com.flashpipelines.core.Envelope;
import com.flashpipelines.core.Service;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class SpringExtension implements Extension {

    private final ApplicationContext applicationContext;

    public SpringExtension(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Props props(Service<Envelope, Envelope> service) {
        return Props.create(SpringActorProducer.class, applicationContext, ActorType.FINALIZER_ACTOR, new Object[] {service});
    }

    public Props props(Service<Envelope, Envelope> service, ActorRef sendTo) {
        return Props.create(SpringActorProducer.class, applicationContext, ActorType.SIMPLE_ACTOR, new Object[] {service, sendTo});
    }

    public Props props(List<ActorReference> actorReferences) {
        return Props.create(SpringActorProducer.class, applicationContext, ActorType.SUPERVISER_ACTOR, new Object[] {actorReferences, this});
    }
}
