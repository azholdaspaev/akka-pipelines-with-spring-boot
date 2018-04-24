package com.flashpipelines.configuration;

import com.typesafe.config.Config;

/**
 * Configuration provider.
 */
public interface ConfigProvider {

    /**
     * Loads configuration.
     *
     * @return loaded {@link Config}
     */
    Config load();
}
