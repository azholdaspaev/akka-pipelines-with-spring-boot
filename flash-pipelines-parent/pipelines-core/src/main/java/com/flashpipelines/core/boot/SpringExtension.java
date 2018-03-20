package com.flashpipelines.core.boot;

import akka.actor.ActorRef;
import akka.actor.Extension;
import akka.actor.Props;
import com.flashpipelines.core.Service;
import com.flashpipelines.core.actor.ActorReference;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static com.flashpipelines.core.actor.ActorType.FINALIZER_ACTOR;
import static com.flashpipelines.core.actor.ActorType.SIMPLE_ACTOR;
import static com.flashpipelines.core.actor.ActorType.SUPERVISER_ACTOR;

public class SpringExtension implements Extension {

    private final ApplicationContext applicationContext;

    public SpringExtension(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Props props(Service service) {
        return Props.create(SpringActorProducer.class, applicationContext, FINALIZER_ACTOR, new Object[] {service});
    }

    public Props props(Service service, ActorRef sendTo) {
        return Props.create(SpringActorProducer.class, applicationContext, SIMPLE_ACTOR, new Object[] {service, sendTo});
    }

    public Props props(List<ActorReference> actorReferences) {
        return Props.create(SpringActorProducer.class, applicationContext, SUPERVISER_ACTOR, new Object[] {actorReferences, this});
    }
}
