package com.tfkfan.nettywebgame.networking.server.handler;

import com.tfkfan.nettywebgame.game.room.GameRoomManager;
import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.message.impl.ConnectMessage;
import com.tfkfan.nettywebgame.networking.message.impl.incoming.IncomingMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@ChannelHandler.Sharable
@Component
public class InitialWebsocketHandler extends WebsocketHandler<WebSocketFrame> {
    public InitialWebsocketHandler(GameRoomManager gameRoomManager) {
        super(gameRoomManager);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof PingWebSocketFrame) {
            ctx.write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        if (frame instanceof TextWebSocketFrame) {
            final Channel channel = ctx.channel();

            String data = ((TextWebSocketFrame) frame).text();
            log.trace("From websocket: " + data);

            Message msg = objectMapper.fromJson(data, IncomingMessage.class);
            int type = msg.getType();
            if (Message.CONNECT == type) {
                log.trace("Connect attempt from {}", channel.remoteAddress());
                join(ctx, new ConnectMessage());
            } else {
                log.error("Invalid event {} sent from remote address {}.",
                        msg.getType(), channel.remoteAddress());
                closeChannelWithLoginFailure(ctx);
            }
        }
    }
}
