package com.flashpipelines.core.metrics;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;

/**
 * Metrics registry.
 */
public interface MetricsRegistry {
    /**
     * Return the {@link Timer} registered under this name; or create and register
     * a new {@link Timer} if none is registered.
     *
     * @param metricsName the name of the metric
     * @return a new or pre-existing {@link Timer}
     */
    Timer timer(String metricsName);

    /**
     * Return the {@link Meter} registered under this name; or create and register
     * a new {@link Meter} if none is registered.
     *
     * @param metricsName the name of the metric
     * @return a new or pre-existing {@link Meter}
     */
    Meter meter(String metricsName);

    /**
     * Return the {@link Histogram} registered under this name; or create and register
     * a new {@link Histogram} if none is registered.
     *
     * @param metricsName the name of the metric
     * @return a new or pre-existing {@link Histogram}
     */
    Histogram histogram(String metricsName);
}
