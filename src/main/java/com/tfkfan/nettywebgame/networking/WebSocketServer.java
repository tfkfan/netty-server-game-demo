package com.tfkfan.nettywebgame.networking;

import com.tfkfan.nettywebgame.config.ApplicationProperties;
import com.tfkfan.nettywebgame.networking.mode.DefaultWebsocketInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketServer {
    private final ApplicationProperties applicationProperties;
    private final DefaultWebsocketInitializer customWebSocketServerInitializer;

    public void start() throws InterruptedException {
        log.info("WebSocketServer is starting...");
        log.info("Reserved {} threads", applicationProperties.getServer().getWorkerThreads()
                + applicationProperties.getServer().getEventLoopThreads()
                + applicationProperties.getServer().getGameThreads());
        EventLoopGroup boss = new NioEventLoopGroup(applicationProperties.getServer().getEventLoopThreads());
        EventLoopGroup worker = new NioEventLoopGroup(applicationProperties.getServer().getWorkerThreads());
        ServerBootstrap boot = new ServerBootstrap();
        boot.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(customWebSocketServerInitializer);
        ChannelFuture future = boot.bind(applicationProperties.getServer().getPort()).sync();
        future.addListener(evt -> log.info("Started ws server, active port:{}", applicationProperties.getServer().getPort()));
        future.channel().closeFuture().addListener((evt) -> {
            log.info("WebSocketSocket is closing...");
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }).sync();
    }
}
