package com.flashpipelines.core;

import java.util.concurrent.CompletableFuture;

public class Envelope {

    private final CompletableFuture<Envelope> waitingResponseFuture;

    private int counter;

    public Envelope(CompletableFuture<Envelope> waitingResponseFuture) {
        this.waitingResponseFuture = waitingResponseFuture;
        this.counter = 0;
    }

    public void increment() {
        counter++;
    }

    public Integer getCounter() {
        return counter;
    }

    public void completeFuture() {
        waitingResponseFuture.complete(this);
    }
}
