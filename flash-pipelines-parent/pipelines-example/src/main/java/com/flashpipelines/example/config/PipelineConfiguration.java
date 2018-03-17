package com.flashpipelines.example.config;

import com.flashpipelines.core.Service;
import com.flashpipelines.core.actor.ActorReference;
import com.flashpipelines.core.pipeline.ActorReferencePipeline;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class PipelineConfiguration {

    @Bean(name = "firstActor")
    public ActorReference firstActor(Config akkaConfiguration, Service firstActorService) {
        return new ActorReference(akkaConfiguration.getConfig("pipeline.actor.pool.first"), firstActorService);
    }

    @Bean
    public Service firstActorService() {
        return envelope -> {
            System.out.println("1");
            return envelope;
        };
    }

    @Bean(name = "secondActor")
    public ActorReference secondActor(Config akkaConfiguration, Service secondActorService) {
        return new ActorReference(akkaConfiguration.getConfig("pipeline.actor.pool.second"), secondActorService);
    }

    @Bean
    public Service secondActorService() {
        return envelope -> {
            System.out.println("2");
            return envelope;
        };
    }

    @Bean(name = "thirdActor")
    public ActorReference thirdActor(Config akkaConfiguration, Service thirdActorService) {
        return new ActorReference(akkaConfiguration.getConfig("pipeline.actor.pool.third"), thirdActorService);
    }

    @Bean
    public Service thirdActorService() {
        return envelope -> {
            System.out.println("3");
            return envelope;
        };
    }

    @Bean(name = "fourthActor")
    public ActorReference fourthActor(Config akkaConfiguration, Service fourthActorService) {
        return new ActorReference(akkaConfiguration.getConfig("pipeline.actor.pool.fourth"), fourthActorService);
    }

    @Bean
    public Service fourthActorService() {
        return envelope -> {
            System.out.println("4");
            return envelope;
        };
    }

    @Bean
    public ActorReferencePipeline pipeline(ActorReference firstActor,
                                           ActorReference secondActor,
                                           ActorReference thirdActor,
                                           ActorReference fourthActor) {

        return new ActorReferencePipeline(Arrays.asList(firstActor, secondActor, thirdActor, fourthActor), ConfigFactory.empty());
    }
}
