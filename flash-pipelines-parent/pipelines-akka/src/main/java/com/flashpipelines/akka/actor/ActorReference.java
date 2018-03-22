package com.flashpipelines.akka.actor;

import com.flashpipelines.akka.Service;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ActorReference {

    private final Configuration configuration;
    private final Service service;

    public ActorReference(Config config, Service service) {
        this.configuration = ConfigBeanFactory.create(config, Configuration.class);
        this.service = service;
    }

    public ActorType getActorType() {
        return configuration.getActorType();
    }

    public Service getService() {
        return service;
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
        private ActorType actorType;
        private int numberOfActors;
        private String name;
    }
}
