package com.flashpipelines.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import com.flashpipelines.core.Envelope;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Scope(value = "prototype")
public class SuperviserActor extends AbstractActor {

    private final ActorRef sendTo;

    public SuperviserActor(List<ActorReference> actorReferences) {
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
        List<ActorReference> reversedActorReferences = new ArrayList<>(actorReferences);
        Collections.reverse(reversedActorReferences);

        ActorRef sendTo = null;
        for (ActorReference actorReference : reversedActorReferences) {
            sendTo = buildActor(actorReference, sendTo);
            getContext().watch(sendTo);
        }
        return sendTo;
    }

    private ActorRef buildActor(ActorReference actorReference, ActorRef router) {
        Props props = new RoundRobinPool(actorReference.getNumberOfActors())
            .props(actorReference.getPropsBuilder().build(router));

        return getContext().actorOf(props, actorReference.getName());
    }
}
