package com.flashpipelines.core.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Router;
import com.flashpipelines.core.Envelope;
import com.flashpipelines.core.boot.SpringExtension;
import com.flashpipelines.core.pipeline.ActorReferencePipeline;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Component
@Scope(value = "prototype")
public class PipelineSupervisorActor extends AbstractActor {

    private final ActorReferencePipeline pipeline;
    private final SpringExtension springExtension;
    private final Router nextRoute;

    public PipelineSupervisorActor(ActorReferencePipeline pipeline, SpringExtension springExtension) {
        this.pipeline = pipeline;
        this.springExtension = springExtension;
        this.nextRoute = buildPipelineRoute(pipeline, springExtension);
    }

    private Router buildPipelineRoute(ActorReferencePipeline pipeline, SpringExtension springExtension) {
        List<ActorReference> actorReferences = pipeline.getActorReferences();

        List<ActorRefRoutee> actorRefRoutees = build(actorReferences.get(actorReferences.size() - 1), 10);


        Router lastRouter = new Router(new RoundRobinRoutingLogic(), Collections.unmodifiableList(actorRefRoutees));


        Router router = lastRouter;
        for (int i = actorReferences.size() - 2; i >=0; i--) {
            List<ActorRefRoutee> routees = build(actorReferences.get(i), router, 10);
            router = new Router(new RoundRobinRoutingLogic(), Collections.unmodifiableList(routees));
        }

        return router;
    }

    private List<ActorRefRoutee> build(ActorReference actorReference, int instances) {
        return IntStream.of(0, instances)
            .mapToObj(i -> {
                ActorRef actorRef = buildActor(actorReference, i);
                getContext().watch(actorRef);
                return actorRef;
            })
            .map(ActorRefRoutee::new)
            .collect(Collectors.toList());
    }

    private List<ActorRefRoutee> build(ActorReference actorReference, Router router, int instances) {
        return IntStream.of(0, instances)
            .mapToObj(i -> {
                ActorRef actorRef = buildActor(actorReference, i, router);
                getContext().watch(actorRef);

                return actorRef;
            })
            .map(ActorRefRoutee::new)
            .collect(Collectors.toList());
    }

    private ActorRef buildActor(ActorReference actorReference, int index) {
        return getContext().actorOf(springExtension.props(
            actorReference.getActorType().getActorBeanName(),
            actorReference.getService(),
            null
        ), actorReference.getName() + index);
    }

    private ActorRef buildActor(ActorReference actorReference, int index, Router router) {
        return getContext().actorOf(springExtension.props(
            actorReference.getActorType().getActorBeanName(),
            actorReference.getService(),
            router
        ), actorReference.getName() + index);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(Envelope.class, envelope -> nextRoute.route(envelope, getSelf()))
            .matchAny(this::unhandled)
            .build();
    }
}
