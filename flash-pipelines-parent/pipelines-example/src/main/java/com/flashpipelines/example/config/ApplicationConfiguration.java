package com.flashpipelines.example.config;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.flashpipelines")
@Import(value = PipelineConfiguration.class)
public class ApplicationConfiguration {

    @Bean
    public ActorSystem actorSystem() {
        return ActorSystem.create("AkkaTaskProcessing", akkaConfiguration());
    }

    @Bean
    public Config akkaConfiguration() {
        return ConfigFactory.load();
    }
}
