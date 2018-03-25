package com.flashpipelines.configuration;

import com.typesafe.config.Config;

public interface ConfigProvider {

    Config load();
}
