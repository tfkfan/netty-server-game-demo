package com.tfkfan.nettywebgame.networking.server.handler;

import com.google.gson.Gson;
import com.tfkfan.nettywebgame.game.room.GameRoomManager;
import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.message.impl.ConnectMessage;
import com.tfkfan.nettywebgame.networking.message.impl.incoming.IncomingMessage;
import com.tfkfan.nettywebgame.networking.message.impl.outcoming.OutcomingMessage;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import com.tfkfan.nettywebgame.shared.FrameUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public abstract class WebsocketHandler<T> extends SimpleChannelInboundHandler<T> {
    protected final Gson objectMapper = new Gson();
    protected final GameRoomManager gameRoomManager;


    protected void join(ChannelHandlerContext ctx, ConnectMessage data) {
        gameRoomManager.addPlayerToWait(PlayerSession.createPlayerSession(ctx.channel(), UUID.randomUUID()), data);
        log.trace("Sending CONNECT_WAIT to channel {}", ctx);
    }

    protected ChannelFuture send(Channel channel, Message message) {
        return channel.writeAndFlush(FrameUtil.eventToFrame(message));
    }

    protected void closeChannelWithLoginFailure(ChannelHandlerContext ctx) {
        log.trace("Channel {} closed due to failure", ctx);
        send(ctx.channel(), new OutcomingMessage(Message.CONNECT_FAILURE, null))
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Error:", cause);
        closeChannelWithLoginFailure(ctx);
    }
}
