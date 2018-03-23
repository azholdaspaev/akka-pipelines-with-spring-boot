package com.flashpipelines.example.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.routing.RoundRobinPool;
import com.flashpipelines.akka.actor.ActorReference;
import com.flashpipelines.akka.boot.SpringExtension;
import com.flashpipelines.akka.builder.PropsBuilder;
import com.flashpipelines.core.Envelope;
import com.flashpipelines.core.Service;
import com.typesafe.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class PipelineConfiguration {

    @Bean(name = "firstActor")
    public ActorReference firstActor(Config akkaConfiguration, PropsBuilder firstPropsBuilder) {
        return new ActorReference(akkaConfiguration.getConfig("pipeline.actor.pool.first"), firstPropsBuilder);
    }

    @Bean
    public Service<Envelope, Envelope> firstActorService() {
        return envelope -> {
            System.out.println("1");
            return envelope;
        };
    }

    @Bean
    public PropsBuilder firstPropsBuilder(SpringExtension springExtension, Service<Envelope, Envelope> firstActorService) {
        return sendTo -> springExtension.props(firstActorService, sendTo);
    }

    @Bean(name = "secondActor")
    public ActorReference secondActor(Config akkaConfiguration, PropsBuilder secondPropsBuilder) {
        return new ActorReference(akkaConfiguration.getConfig("pipeline.actor.pool.second"), secondPropsBuilder);
    }

    @Bean
    public Service<Envelope, Envelope> secondActorService() {
        return envelope -> {
            System.out.println("2");
            return envelope;
        };
    }

    @Bean
    public PropsBuilder secondPropsBuilder(SpringExtension springExtension, Service<Envelope, Envelope> secondActorService) {
        return sendTo -> springExtension.props(secondActorService, sendTo);
    }

    @Bean(name = "thirdActor")
    public ActorReference thirdActor(Config akkaConfiguration, PropsBuilder thirdPropsBuilder) {
        return new ActorReference(akkaConfiguration.getConfig("pipeline.actor.pool.third"), thirdPropsBuilder);
    }

    @Bean
    public Service<Envelope, Envelope> thirdActorService() {
        return envelope -> {
            System.out.println("3");
            return envelope;
        };
    }

    @Bean
    public PropsBuilder thirdPropsBuilder(SpringExtension springExtension, Service<Envelope, Envelope> thirdActorService) {
        return sendTo -> springExtension.props(thirdActorService, sendTo);
    }

    @Bean(name = "fourthActor")
    public ActorReference fourthActor(Config akkaConfiguration, PropsBuilder fourthPropsBuilder) {
        return new ActorReference(akkaConfiguration.getConfig("pipeline.actor.pool.fourth"), fourthPropsBuilder);
    }

    @Bean
    public Service<Envelope, Envelope> fourthActorService() {
        return envelope -> {
            System.out.println("4");
            return envelope;
        };
    }

    @Bean
    public PropsBuilder fourthPropsBuilder(SpringExtension springExtension, Service<Envelope, Envelope> fourthActorService) {
        return sendTo -> springExtension.props(fourthActorService);
    }

    @Bean
    public List<ActorReference> pipeline(ActorReference firstActor,
                                         ActorReference secondActor,
                                         ActorReference thirdActor,
                                         ActorReference fourthActor) {

        return Arrays.asList(firstActor, secondActor, thirdActor, fourthActor);
    }

    @Bean
    public ActorRef pipelineRouter(List<ActorReference> pipeline,
                                   ActorSystem actorSystem,
                                   SpringExtension springExtension) {

        return actorSystem.actorOf(springExtension.props(pipeline).withRouter(new RoundRobinPool(1)), "superviser");
    }
}
