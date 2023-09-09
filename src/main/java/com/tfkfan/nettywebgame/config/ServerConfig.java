package com.tfkfan.nettywebgame.config;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class ServerConfig {
    private final ApplicationProperties applicationProperties;
    @Bean
    public ScheduledExecutorService taskManagerService(){
        return new NioEventLoopGroup(applicationProperties.getServer().getGameThreads(), Executors.newCachedThreadPool());
    }
}
