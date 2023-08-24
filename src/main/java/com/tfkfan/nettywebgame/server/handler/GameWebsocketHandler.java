package com.tfkfan.nettywebgame.server.handler;


import com.tfkfan.nettywebgame.event.DisconnectEvent;
import com.tfkfan.nettywebgame.game.room.GameRoomManager;
import com.tfkfan.nettywebgame.networking.message.PlayerMessage;
import com.tfkfan.nettywebgame.shared.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@NoArgsConstructor
@Component
@ChannelHandler.Sharable
public class GameWebsocketHandler extends SimpleChannelInboundHandler<PlayerMessage> {
    private GameRoomManager gameRoomManager;

    @Autowired
    public void setGameRoomManager(@Lazy GameRoomManager gameRoomManager) {
        this.gameRoomManager = gameRoomManager;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, PlayerMessage msg) {
        var room = gameRoomManager.getRoomByKey(msg.getSession().getParentRoomKey());
        if (room != null)
            room.onMessage(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        var event = new DisconnectEvent(SessionUtil.getPlayerSessionFromChannel(ctx));
        var room = gameRoomManager.getRoomByKey(event.getSession().getParentRoomKey());
        if (room != null)
            room.onDisconnect(event.getSession());
    }
}

