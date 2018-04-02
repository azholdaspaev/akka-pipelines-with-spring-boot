package com.flashpipelines.core.metrics.registry;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.flashpipelines.core.metrics.MetricsRegistry;

/**
 * Codahale metrics registry.
 */
public class CodahaleMetricsRegistry implements MetricsRegistry {

    private final MetricRegistry metricRegistry;

    public CodahaleMetricsRegistry(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @Override
    public Timer timer(String metricsName) {
        return metricRegistry.timer(metricsName);
    }

    @Override
    public Meter meter(String metricsName) {
        return metricRegistry.meter(metricsName);
    }

    @Override
    public Histogram histogram(String metricsName) {
        return metricRegistry.histogram(metricsName);
    }
}
