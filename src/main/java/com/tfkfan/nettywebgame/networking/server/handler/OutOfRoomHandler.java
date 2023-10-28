package com.tfkfan.nettywebgame.networking.server.handler;

import com.tfkfan.nettywebgame.event.GameRoomJoinEvent;
import com.tfkfan.nettywebgame.event.dispatcher.EventDispatcher;
import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.message.impl.incoming.IncomingPlayerMessage;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import com.tfkfan.nettywebgame.service.GameRoomService;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable
public class OutOfRoomHandler extends AbstractHandler<IncomingPlayerMessage> {
    protected GameRoomService gameRoomService;

    public OutOfRoomHandler() {
        super(new EventDispatcher<>());
        addEventListener(Message.CONNECT, GameRoomJoinEvent.class, this::handleJoin);
    }

    @Autowired
    public void setGameRoomManager(@Lazy GameRoomService gameRoomService) {
        this.gameRoomService = gameRoomService;
    }

    protected void handleJoin(GameRoomJoinEvent event) {
        gameRoomService.addPlayerToWait(PlayerSession.getPlayerSessionFromChannel(event.getChannel()), event);
    }
}

