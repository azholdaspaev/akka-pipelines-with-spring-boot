package com.flashpipelines.core.actor;

import com.flashpipelines.core.Service;
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

    public String getName() {
        return configuration.getName();
    }

    @Data
    @NoArgsConstructor
    protected static class Configuration {
        private ActorType actorType;
        private String name;
    }
}
