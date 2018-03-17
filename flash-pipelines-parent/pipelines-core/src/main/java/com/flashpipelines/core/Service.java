package com.flashpipelines.core;

@FunctionalInterface
public interface Service {

    Envelope apply(Envelope envelope);
}
