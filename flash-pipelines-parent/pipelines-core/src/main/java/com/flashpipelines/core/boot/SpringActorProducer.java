package com.flashpipelines.core.boot;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import akka.routing.Router;
import com.flashpipelines.core.Service;
import com.flashpipelines.core.pipeline.ActorReferencePipeline;
import org.springframework.context.ApplicationContext;

public class SpringActorProducer implements IndirectActorProducer {

    private final ApplicationContext applicationContext;
    private final String beanName;
    private final Service service;
    private final Router router;
    private final ActorReferencePipeline referencePipeline;
    private final SpringExtension springExtension;

    public SpringActorProducer(ApplicationContext applicationContext,
                               String beanName,
                               Service service,
                               Router router,
                               ActorReferencePipeline referencePipeline,
                               SpringExtension springExtension) {
        this.applicationContext = applicationContext;
        this.beanName = beanName;
        this.service = service;
        this.router = router;
        this.referencePipeline = referencePipeline;
        this.springExtension = springExtension;
    }

    @Override
    public Actor produce() {
        if (referencePipeline != null && springExtension != null) {
            return (Actor) applicationContext.getBean(beanName, referencePipeline, springExtension);
        } else if (service == null && router == null) {
            return (Actor) applicationContext.getBean(beanName);
        } else if (router == null) {
            return (Actor) applicationContext.getBean(beanName, service);
        } else  {
            return (Actor) applicationContext.getBean(beanName, service, router);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Actor> actorClass() {
        return (Class<? extends Actor>) applicationContext.getType(beanName);
    }
}
