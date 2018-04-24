package com.flashpipelines.akka.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import com.flashpipelines.akka.props.PropsBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contains information for actor configuration.
 */
public class ActorReference {

    private final Configuration configuration;
    private final PropsBuilder propsBuilder;

    public ActorReference(Config config, PropsBuilder propsBuilder) {
        this.configuration = ConfigBeanFactory.create(config, Configuration.class);
        this.propsBuilder = propsBuilder;
    }

    /**
     * Builds {@link Props}.
     *
     * @param actorReference actor, which will receive messages.
     */
    public Props buildProps(ActorRef actorReference) {
        return propsBuilder.build(actorReference);
    }

    public String getName() {
        return configuration.getName();
    }

    /**
     * Configuration.
     */
    @Data
    @NoArgsConstructor
    protected static class Configuration {
        private String name;
    }
}
