package com.flashpipelines.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import com.flashpipelines.akka.boot.SpringExtension;
import com.flashpipelines.core.Envelope;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(value = "prototype")
public class SuperviserActor extends AbstractActor {

    private final SpringExtension springExtension;
    private final ActorRef sendTo;

    public SuperviserActor(List<ActorReference> actorReferences, SpringExtension springExtension) {
        this.springExtension = springExtension;
        this.sendTo = buildPipelineRoute(actorReferences);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(Envelope.class, envelope -> sendTo.tell(envelope, getSelf()))
            .matchAny(this::unhandled)
            .build();
    }

    private ActorRef buildPipelineRoute(List<ActorReference> actorReferences) {
        ActorRef actor = buildActor(actorReferences.get(actorReferences.size() - 1));
        getContext().watch(actor);

        ActorRef router = actor;
        for (int i = actorReferences.size() - 2; i >=0; i--) {
            ActorRef routees = buildActor(actorReferences.get(i), router);
            getContext().watch(routees);

            router = routees;
        }

        return router;
    }

    private ActorRef buildActor(ActorReference actorReference) {
        Props props = new RoundRobinPool(actorReference.getNumberOfActors())
            .props(springExtension.props(actorReference.getService()));

        return getContext().actorOf(props, actorReference.getName());
    }

    private ActorRef buildActor(ActorReference actorReference, ActorRef router) {
        Props props = new RoundRobinPool(actorReference.getNumberOfActors())
            .props(springExtension.props(actorReference.getService(), router));

        return getContext().actorOf(props, actorReference.getName());
    }
}
