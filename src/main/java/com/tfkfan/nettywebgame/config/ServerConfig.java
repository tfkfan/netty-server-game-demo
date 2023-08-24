package com.tfkfan.nettywebgame.config;

import com.tfkfan.nettywebgame.task.SimpleTaskManagerService;
import com.tfkfan.nettywebgame.task.TaskManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executors;

@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class ServerConfig {
    private final ApplicationProperties applicationProperties;
    @Bean
    public TaskManagerService taskManagerService(){
        return new SimpleTaskManagerService(applicationProperties.getServer().getGameThreads(), Executors.newCachedThreadPool());
    }
}
