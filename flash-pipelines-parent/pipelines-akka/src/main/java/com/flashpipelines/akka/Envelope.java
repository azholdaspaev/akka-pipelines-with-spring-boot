package com.flashpipelines.akka;

import java.util.HashMap;
import java.util.Map;

public class Envelope {

    private final Map<String, Object> data;

    public Envelope() {
        data = new HashMap<>();
    }
}
