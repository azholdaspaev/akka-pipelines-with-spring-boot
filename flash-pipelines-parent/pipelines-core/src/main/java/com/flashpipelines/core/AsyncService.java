package com.flashpipelines.core;

import java.util.concurrent.CompletableFuture;

public interface AsyncService {

    CompletableFuture<Envelope> apply(Envelope envelope);
}
