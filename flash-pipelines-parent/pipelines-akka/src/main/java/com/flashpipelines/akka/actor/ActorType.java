package com.flashpipelines.akka.actor;

import akka.actor.Actor;

public enum ActorType {

    SIMPLE_ACTOR(SimpleActor.class, "simpleActor"),
    FINALIZER_ACTOR(FinalizerActor.class, "finalizerActor"),
    SUPERVISER_ACTOR(SuperviserActor.class, "superviserActor");

    private final Class<? extends Actor> actorClass;
    private final String actorBeanName;

    ActorType(Class<? extends Actor> actorClass, String actorBeanName) {
        this.actorClass = actorClass;
        this.actorBeanName = actorBeanName;
    }

    public Class<? extends Actor> getActorClass() {
        return actorClass;
    }

    public String getActorBeanName() {
        return actorBeanName;
    }
}
