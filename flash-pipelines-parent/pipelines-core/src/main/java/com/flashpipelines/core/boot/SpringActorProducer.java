package com.flashpipelines.core.boot;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import com.flashpipelines.core.actor.ActorType;
import org.springframework.context.ApplicationContext;

public class SpringActorProducer implements IndirectActorProducer {

    private final ApplicationContext applicationContext;
    private final ActorType actorType;
    private final Object[] args;

    public SpringActorProducer(ApplicationContext applicationContext, ActorType actorType, Object[] args) {
        this.applicationContext = applicationContext;
        this.actorType = actorType;
        this.args = args;
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
