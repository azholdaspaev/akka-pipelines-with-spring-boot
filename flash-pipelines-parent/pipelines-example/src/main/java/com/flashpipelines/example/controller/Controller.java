package com.flashpipelines.example.controller;

import akka.actor.ActorRef;
import com.flashpipelines.core.Envelope;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RestController
public class Controller {

    private final ActorRef pipelineRouter;

    public Controller(@Qualifier("pipelineRouter") ActorRef pipelineRouter) {
        this.pipelineRouter = pipelineRouter;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/api", produces = "application/json")
    public Future<Integer> getCounter() {
        CompletableFuture<Envelope> waitingResponseFuture = new CompletableFuture<>();

        pipelineRouter.tell(new Envelope(waitingResponseFuture), ActorRef.noSender());

        return waitingResponseFuture
            .thenApply(Envelope::getCounter);
    }
}
