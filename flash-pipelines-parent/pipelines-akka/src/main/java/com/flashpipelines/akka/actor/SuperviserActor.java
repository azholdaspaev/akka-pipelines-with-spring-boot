package com.flashpipelines.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.routing.FromConfig;
import com.flashpipelines.core.Envelope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Superviser actor.
 */
public final class SuperviserActor extends AbstractActor {

    private final ActorRef sendTo;

    private SuperviserActor(List<ActorReference> actorReferences) {
        this.sendTo = buildPipelineRoute(actorReferences);
    }

    /**
     * Static factory for actor's {@link Props}.
     *
     * @param actorReferences pipeline of {@link ActorReference}
     * @return new {@link Props} instance.
     */
    public static Props props(List<ActorReference> actorReferences) {
        return Props.create(SuperviserActor.class, actorReferences);
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
        Props props = actorReference.buildProps(router);

        return getContext().actorOf(FromConfig.getInstance().props(props), actorReference.getName());
    }
}
