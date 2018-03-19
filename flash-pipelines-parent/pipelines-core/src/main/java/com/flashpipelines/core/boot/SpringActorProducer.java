package com.flashpipelines.core.boot;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.IndirectActorProducer;
import com.flashpipelines.core.Service;
import com.flashpipelines.core.actor.ActorReference;
import com.flashpipelines.core.actor.ActorType;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class SpringActorProducer implements IndirectActorProducer {

    private final ApplicationContext applicationContext;
    private final ActorType actorType;
    private final Object[] args;

    public SpringActorProducer(ApplicationContext applicationContext, ActorType actorType, Service service) {
        this.applicationContext = applicationContext;
        this.actorType = actorType;
        this.args = new Object[] {service};
    }

    public SpringActorProducer(ApplicationContext applicationContext, ActorType actorType, Service service, ActorRef actorRef) {
        this.applicationContext = applicationContext;
        this.actorType = actorType;
        this.args = new Object[] {service, actorRef};
    }

    public SpringActorProducer(ApplicationContext applicationContext, ActorType actorType, List<ActorReference> actorReferences, SpringExtension springExtension) {
        this.applicationContext = applicationContext;
        this.actorType = actorType;
        this.args = new Object[] {actorReferences, springExtension};
    }

    @Override
    public Actor produce() {
        return applicationContext.getBean(actorType.getActorClass(), args);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Actor> actorClass() {
        return (Class<? extends Actor>) applicationContext.getType(actorType.getActorBeanName());
    }
}
