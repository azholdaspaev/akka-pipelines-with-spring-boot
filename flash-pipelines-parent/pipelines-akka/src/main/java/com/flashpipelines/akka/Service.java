package com.flashpipelines.akka;

@FunctionalInterface
public interface Service {

    Envelope apply(Envelope envelope);
}
