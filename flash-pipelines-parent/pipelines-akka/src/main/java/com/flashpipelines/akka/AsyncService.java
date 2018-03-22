package com.flashpipelines.akka;

import java.util.concurrent.CompletableFuture;

public interface AsyncService {

    CompletableFuture<Envelope> apply(Envelope envelope);
}
