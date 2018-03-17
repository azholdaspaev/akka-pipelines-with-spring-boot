package com.flashpipelines.core.pipeline;

import com.flashpipelines.core.actor.ActorReference;
import com.typesafe.config.Config;

import java.util.Collections;
import java.util.List;

public class ActorReferencePipeline {

    private final List<ActorReference> actorReferences;
    private final Config config;

    public ActorReferencePipeline(List<ActorReference> actorReferences, Config config) {
        this.actorReferences = actorReferences;
        this.config = config;
    }

    public List<ActorReference> getActorReferences() {
        return Collections.unmodifiableList(actorReferences);
    }

    public Config getConfig() {
        return config;
    }
}
