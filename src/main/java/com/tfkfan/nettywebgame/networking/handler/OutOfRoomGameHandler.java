package com.tfkfan.nettywebgame.networking.handler;

import com.tfkfan.nettywebgame.event.GameRoomJoinEvent;
import com.tfkfan.nettywebgame.event.dispatcher.EventDispatcher;
import com.tfkfan.nettywebgame.networking.message.MessageType;
import com.tfkfan.nettywebgame.networking.message.impl.incoming.IncomingPlayerMessage;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import com.tfkfan.nettywebgame.service.GameRoomManagementService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable
public class OutOfRoomGameHandler extends AbstractGameHandler<IncomingPlayerMessage> {
    protected GameRoomManagementService gameRoomManagementService;

    public OutOfRoomGameHandler() {
        super(new EventDispatcher<>());
        addEventListener(MessageType.JOIN, GameRoomJoinEvent.class,
                event -> gameRoomManagementService.addPlayerToWait(event));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        gameRoomManagementService.removePlayerFromWaitQueue(PlayerSession.getPlayerSessionFromChannel(ctx));
    }

    @Autowired
    public void setGameRoomManager(@Lazy GameRoomManagementService gameRoomManagementService) {
        this.gameRoomManagementService = gameRoomManagementService;
    }
}

