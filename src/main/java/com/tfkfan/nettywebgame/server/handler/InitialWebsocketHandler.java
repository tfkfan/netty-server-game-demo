package com.tfkfan.nettywebgame.server.handler;

import com.google.gson.Gson;
import com.tfkfan.nettywebgame.game.room.GameRoomManager;
import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.message.impl.ConnectMessage;
import com.tfkfan.nettywebgame.networking.message.impl.incoming.IncomingMessage;
import com.tfkfan.nettywebgame.networking.message.impl.outcoming.OutcomingMessage;
import com.tfkfan.nettywebgame.shared.FrameUtil;
import com.tfkfan.nettywebgame.shared.SessionUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@ChannelHandler.Sharable
@Component
public class InitialWebsocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private final Gson objectMapper = new Gson();
    private final GameRoomManager gameRoomManager;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {
        try {
            if (frame instanceof PingWebSocketFrame) {
                ctx.write(new PongWebSocketFrame(frame.content().retain()));
                return;
            }

            if (frame instanceof TextWebSocketFrame) {
                Channel channel = ctx.channel();

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
        } catch (Exception e) {
            log.error("", e);
            closeChannelWithLoginFailure(ctx);
        }
    }

    public void join(ChannelHandlerContext ctx, ConnectMessage data) {
        gameRoomManager.addPlayerToWait(SessionUtil.createPlayerSession(ctx.channel(), UUID.randomUUID()), data);
        log.trace("Sending CONNECT_WAIT to channel {}", ctx);
    }

    protected ChannelFuture send(Channel channel, Message message) {
        return channel.writeAndFlush(FrameUtil.eventToFrame(message));
    }

    protected void closeChannelWithLoginFailure(ChannelHandlerContext ctx) {
        send(ctx.channel(), new OutcomingMessage(Message.CONNECT_FAILURE, null))
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Error:", cause);
    }
}
