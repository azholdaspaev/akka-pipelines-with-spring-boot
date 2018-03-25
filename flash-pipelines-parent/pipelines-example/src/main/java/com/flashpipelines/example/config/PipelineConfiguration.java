package com.flashpipelines.example.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;
import com.flashpipelines.akka.actor.ActorReference;
import com.flashpipelines.akka.actor.AsyncActor;
import com.flashpipelines.akka.actor.FinalizerActor;
import com.flashpipelines.akka.actor.SimpleActor;
import com.flashpipelines.akka.actor.SuperviserActor;
import com.flashpipelines.akka.props.PropsBuilder;
import com.flashpipelines.core.Envelope;
import com.flashpipelines.core.Service;
import com.typesafe.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Configuration
public class PipelineConfiguration {

    @Bean(name = "firstActor")
    public ActorReference firstActor(Config akkaConfiguration, PropsBuilder firstPropsBuilder) {
        return new ActorReference(akkaConfiguration.getConfig("actor.pool.first"), firstPropsBuilder);
    }

    @Bean
    public Service<Envelope, Envelope> firstActorService() {
        return envelope -> {
            System.out.println("1");
            return envelope;
        };
    }

    @Bean
    public PropsBuilder firstPropsBuilder(Service<Envelope, Envelope> firstActorService) {
        return sendTo -> SimpleActor.props(firstActorService, sendTo);
    }

    @Bean(name = "secondActor")
    public ActorReference secondActor(Config akkaConfiguration, PropsBuilder secondPropsBuilder) {
        return new ActorReference(akkaConfiguration.getConfig("actor.pool.second"), secondPropsBuilder);
    }

    @Bean
    public Service<Envelope, Envelope> secondActorService() {
        return envelope -> {
            System.out.println("2");
            return envelope;
        };
    }

    @Bean
    public PropsBuilder secondPropsBuilder(Service<Envelope, Envelope> secondActorService) {
        return sendTo -> SimpleActor.props(secondActorService, sendTo);
    }

    @Bean(name = "thirdActor")
    public ActorReference thirdActor(Config akkaConfiguration, PropsBuilder thirdPropsBuilder) {
        return new ActorReference(akkaConfiguration.getConfig("actor.pool.third"), thirdPropsBuilder);
    }

    @Bean
    public Service<Envelope, CompletableFuture<Envelope>> thirdActorService() {
        return envelope -> {
            System.out.println("3");
            return CompletableFuture.completedFuture(envelope);
        };
    }

    @Bean
    public PropsBuilder thirdPropsBuilder(Service<Envelope, CompletableFuture<Envelope>> thirdActorService) {
        return sendTo -> AsyncActor.props(thirdActorService, sendTo);
    }

    @Bean(name = "fourthActor")
    public ActorReference fourthActor(Config akkaConfiguration, PropsBuilder fourthPropsBuilder) {
        return new ActorReference(akkaConfiguration.getConfig("actor.pool.fourth"), fourthPropsBuilder);
    }

    @Bean
    public Service<Envelope, Envelope> fourthActorService() {
        return envelope -> {
            System.out.println("4");
            return envelope;
        };
    }

    @Bean
    public PropsBuilder fourthPropsBuilder(Service<Envelope, Envelope> fourthActorService) {
        return sendTo -> FinalizerActor.props(fourthActorService);
    }

    @Bean
    public List<ActorReference> pipeline(ActorReference firstActor,
                                         ActorReference secondActor,
                                         ActorReference thirdActor,
                                         ActorReference fourthActor) {

        return Arrays.asList(firstActor, secondActor, thirdActor, fourthActor);
    }

    @Bean
    public ActorRef pipelineRouter(List<ActorReference> pipeline, ActorSystem actorSystem) {
        Props props = SuperviserActor.props(pipeline);

        return actorSystem.actorOf(FromConfig.getInstance().props(props), "superviser");
    }
}
