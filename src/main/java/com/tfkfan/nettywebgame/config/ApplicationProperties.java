package com.tfkfan.nettywebgame.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application")
@ConfigurationPropertiesScan
public class ApplicationProperties {
    public static @Data @NoArgsConstructor class RoomProperties {
        private Long loopRate = Constants.DEFAULT_LOOPRATE;
        private Long initDelay = Constants.ROOM_INIT_DELAY;
        private Long startDelay = Constants.ROOM_START_DELAY;
        private Long maxPlayers = Constants.MAX_PLAYERS;
        private Long endDelay = Constants.END_DELAY;
    }

    public static @Data @NoArgsConstructor class ServerProperties {
        private Integer port = 8080;
        private Integer eventLoopThreads = 1;
        private Integer workerThreads = 2;
        private Integer gameThreads = 4;
    }

    private RoomProperties room;
    private ServerProperties server;
}
