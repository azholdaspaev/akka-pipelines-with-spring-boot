package com.flashpipelines.akka.builder;

import akka.actor.ActorRef;
import akka.actor.Props;

@FunctionalInterface
public interface PropsBuilder {

    Props build(ActorRef sendTo);
}
