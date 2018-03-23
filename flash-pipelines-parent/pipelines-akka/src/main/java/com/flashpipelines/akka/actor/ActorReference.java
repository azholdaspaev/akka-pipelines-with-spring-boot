package com.flashpipelines.akka.actor;

import com.flashpipelines.akka.builder.PropsBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ActorReference {

    private final Configuration configuration;
    private final PropsBuilder propsBuilder;

    public ActorReference(Config config, PropsBuilder propsBuilder) {
        this.configuration = ConfigBeanFactory.create(config, Configuration.class);
        this.propsBuilder = propsBuilder;
    }

    public PropsBuilder getPropsBuilder() {
        return propsBuilder;
    }

    public int getNumberOfActors() {
        return configuration.getNumberOfActors();
    }

    public String getName() {
        return configuration.getName();
    }

    @Data
    @NoArgsConstructor
    protected static class Configuration {
        private int numberOfActors;
        private String name;
    }
}
