package com.flashpipelines.core.boot;

import akka.actor.ActorRef;
import akka.actor.Extension;
import akka.actor.Props;
import com.flashpipelines.core.Service;
import com.flashpipelines.core.actor.ActorReference;
import com.flashpipelines.core.actor.ActorType;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class SpringExtension implements Extension {

    private final ApplicationContext applicationContext;

    public SpringExtension(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Props props(Service service) {
        return Props.create(SpringActorProducer.class, applicationContext, ActorType.FINALIZER_ACTOR, service);
    }

    public Props props(Service service, ActorRef sendTo) {
        return Props.create(SpringActorProducer.class, applicationContext, ActorType.SIMPLE_ACTOR, service, sendTo);
    }

    public Props props(List<ActorReference> actorReferences) {
        return Props.create(SpringActorProducer.class, applicationContext, ActorType.SUPERVISER_ACTOR, actorReferences, this);
    }
}
