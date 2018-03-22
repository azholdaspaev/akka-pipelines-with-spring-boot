package com.flashpipelines.akka.actor;

import com.flashpipelines.core.Envelope;
import com.flashpipelines.core.Service;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ActorReference {

    private final Configuration configuration;
    private final Service<Envelope, Envelope> service;

    public ActorReference(Config config, Service<Envelope, Envelope> service) {
        this.configuration = ConfigBeanFactory.create(config, Configuration.class);
        this.service = service;
    }

    public Service<Envelope, Envelope> getService() {
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
        private int numberOfActors;
        private String name;
    }
}
