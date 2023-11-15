package com.tfkfan.nettywebgame.networking.handler;


import com.tfkfan.nettywebgame.event.InitPlayerEvent;
import com.tfkfan.nettywebgame.event.KeyDownPlayerEvent;
import com.tfkfan.nettywebgame.event.dispatcher.EventDispatcher;
import com.tfkfan.nettywebgame.networking.message.MessageType;
import com.tfkfan.nettywebgame.networking.message.PlayerMessage;
import com.tfkfan.nettywebgame.service.GameRoomManagementService;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable
public class GameGameHandler extends AbstractGameHandler<PlayerMessage> {
    private GameRoomManagementService gameRoomManagementService;

    public GameGameHandler() {
        super(new EventDispatcher<>());
        addEventListener(MessageType.INIT, InitPlayerEvent.class,
                event -> gameRoomManagementService.getRoomByKey(event.getSession().getRoomKey()).ifPresent(room -> room.onPlayerInitRequest(event)));
        addEventListener(MessageType.PLAYER_KEY_DOWN, KeyDownPlayerEvent.class,
                event -> gameRoomManagementService.getRoomByKey(event.getSession().getRoomKey()).ifPresent(room -> room.onPlayerKeyDown(event)));
    }

    @Autowired
    public void setGameRoomService(@Lazy GameRoomManagementService gameRoomManagementService) {
        this.gameRoomManagementService = gameRoomManagementService;
    }
}

