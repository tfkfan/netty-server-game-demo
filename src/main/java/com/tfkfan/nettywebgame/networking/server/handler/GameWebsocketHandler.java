package com.tfkfan.nettywebgame.networking.server.handler;


import com.tfkfan.nettywebgame.game.room.GameRoomManager;
import com.tfkfan.nettywebgame.networking.message.PlayerMessage;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@NoArgsConstructor
@ChannelHandler.Sharable
public class GameWebsocketHandler extends WebsocketHandler<PlayerMessage> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, PlayerMessage msg) {
        gameRoomManager.getRoomByKey(msg.getSession().getParentRoomKey())
                .ifPresent(room -> room.onMessage(msg));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        final PlayerSession session = PlayerSession.getPlayerSessionFromChannel(ctx);
        gameRoomManager.getRoomByKey(session.getParentRoomKey())
                .ifPresentOrElse(room -> room.onDisconnect(session), session::removePlayerSessionFromChannel);
    }
}

