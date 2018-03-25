package com.flashpipelines.configuration.local;

import com.flashpipelines.configuration.ConfigProvider;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class LocalConfigProvider implements ConfigProvider {

    @Override
    public Config load() {
        return ConfigFactory.load();
    }
}
