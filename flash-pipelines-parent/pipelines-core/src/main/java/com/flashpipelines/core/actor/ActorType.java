package com.flashpipelines.core.actor;

import akka.actor.Actor;

public enum ActorType {

    SIMPLE_ACTOR(SimpleActor.class, "simpleActor"),
    ENDING_ACTOR(EndingActor.class, "endingActor");

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
