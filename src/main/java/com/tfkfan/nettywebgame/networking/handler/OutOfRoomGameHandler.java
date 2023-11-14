package com.tfkfan.nettywebgame.networking.handler;

import com.tfkfan.nettywebgame.event.GameRoomJoinEvent;
import com.tfkfan.nettywebgame.event.dispatcher.EventDispatcher;
import com.tfkfan.nettywebgame.networking.message.MessageType;
import com.tfkfan.nettywebgame.networking.message.impl.incoming.IncomingPlayerMessage;
import com.tfkfan.nettywebgame.service.GameRoomService;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable
public class OutOfRoomGameHandler extends AbstractGameHandler<IncomingPlayerMessage> {
    protected GameRoomService gameRoomService;

    public OutOfRoomGameHandler() {
        super(new EventDispatcher<>());
        addEventListener(MessageType.CONNECT, GameRoomJoinEvent.class,
                event -> gameRoomService.addPlayerToWait(event));
    }

    @Autowired
    public void setGameRoomManager(@Lazy GameRoomService gameRoomService) {
        this.gameRoomService = gameRoomService;
    }
}

