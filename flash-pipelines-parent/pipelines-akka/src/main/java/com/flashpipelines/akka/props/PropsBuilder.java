package com.flashpipelines.akka.props;

import akka.actor.ActorRef;
import akka.actor.Props;

/**
 * Builder of {@link Props}.
 */
@FunctionalInterface
public interface PropsBuilder {

    /**
     * Builds particular actor {@link Props}.
     *
     * @param sendTo the next actor in pipeline
     * @return new {@link Props} instance.
     */
    Props build(ActorRef sendTo);
}
