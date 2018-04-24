package com.flashpipelines.example.config;

import akka.actor.ActorSystem;
import com.flashpipelines.configuration.ConfigProvider;
import com.flashpipelines.configuration.local.LocalConfigProvider;
import com.typesafe.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Application configuration.
 */
@Configuration
@ComponentScan(basePackages = "com.flashpipelines")
@Import(value = PipelineConfiguration.class)
public class ApplicationConfiguration {

    /**
     * Actor system initializer bean.
     */
    @Bean
    public ActorSystem actorSystem(Config akkaConfiguration) {
        return ActorSystem.create("AkkaTaskProcessing", akkaConfiguration);
    }

    /**
     * Configuration loader bean.
     */
    @Bean
    public Config akkaConfiguration(ConfigProvider configProvider) {
        return configProvider.load();
    }

    /**
     * Configuration provider bean.
     */
    @Bean
    public ConfigProvider configProvider() {
        return new LocalConfigProvider();
    }
}
