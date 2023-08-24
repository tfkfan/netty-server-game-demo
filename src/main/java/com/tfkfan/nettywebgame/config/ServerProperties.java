package com.tfkfan.nettywebgame.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServerProperties {
    private Integer port = 8080;
    private Integer eventLoopThreads = 1;
    private Integer workerThreads = 2;
    private Integer gameThreads = 4;
}
