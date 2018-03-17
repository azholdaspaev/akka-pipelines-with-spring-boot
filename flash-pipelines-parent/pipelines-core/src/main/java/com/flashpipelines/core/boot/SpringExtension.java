package com.flashpipelines.core.boot;

import akka.actor.Extension;
import akka.actor.Props;
import akka.routing.Router;
import com.flashpipelines.core.Service;
import com.flashpipelines.core.pipeline.ActorReferencePipeline;
import org.springframework.context.ApplicationContext;

public class SpringExtension implements Extension {

    private final ApplicationContext applicationContext;

    public SpringExtension(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Props props(String actorBeanName) {
        return Props.create(SpringActorProducer.class, applicationContext, actorBeanName, null, null);
    }

    public Props props(String actorBeanName, Service service) {
        return Props.create(SpringActorProducer.class, applicationContext, actorBeanName, service, null, null);
    }

    public Props props(String actorBeanName, Service service, Router router) {
        return Props.create(SpringActorProducer.class, applicationContext, actorBeanName, service, router, null, null);
    }

    public Props props(String actorBeanName, ActorReferencePipeline actorReferencePipeline, SpringExtension springExtension) {
        return Props.create(SpringActorProducer.class, applicationContext, actorBeanName, null, null, actorReferencePipeline, springExtension);
    }
}
