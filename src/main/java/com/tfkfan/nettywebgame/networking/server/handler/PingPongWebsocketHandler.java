package com.tfkfan.nettywebgame.networking.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@ChannelHandler.Sharable
@Component
public class PingPongWebsocketHandler extends SimpleChannelInboundHandler<PingWebSocketFrame> {
    @Override
    public void channelRead0(ChannelHandlerContext ctx, PingWebSocketFrame frame) {
        ctx.write(new PongWebSocketFrame(frame.content().retain()));
    }
}
