package com.tfkfan.nettywebgame.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final ApplicationProperties applicationProperties;
    @Bean
    public ScheduledExecutorService taskManagerService(){
        return new ScheduledThreadPoolExecutor(applicationProperties.getServer().getGameThreads());
    }
}
