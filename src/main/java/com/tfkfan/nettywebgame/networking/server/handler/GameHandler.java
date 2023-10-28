package com.tfkfan.nettywebgame.networking.server.handler;


import com.tfkfan.nettywebgame.event.InitPlayerEvent;
import com.tfkfan.nettywebgame.event.KeyDownPlayerEvent;
import com.tfkfan.nettywebgame.event.MouseDownPlayerEvent;
import com.tfkfan.nettywebgame.event.MouseMovePlayerEvent;
import com.tfkfan.nettywebgame.event.dispatcher.EventDispatcher;
import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.message.PlayerMessage;
import com.tfkfan.nettywebgame.service.GameRoomService;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable
public class GameHandler extends AbstractHandler<PlayerMessage> {

    private GameRoomService gameRoomService;

    public GameHandler() {
        super(new EventDispatcher<>());
        addEventListener(Message.INIT, InitPlayerEvent.class,
                this::onPlayerInitRequest);
        addEventListener(Message.PLAYER_KEY_DOWN, KeyDownPlayerEvent.class,
                this::onPlayerKeyDown);
        addEventListener(Message.PLAYER_MOUSE_DOWN, MouseDownPlayerEvent.class,
                this::onPlayerMouseDown);
        addEventListener(Message.PLAYER_MOUSE_MOVE, MouseMovePlayerEvent.class,
                this::onPlayerMouseMove);
    }

    @Autowired
    public void setGameRoomService(@Lazy GameRoomService gameRoomService) {
        this.gameRoomService = gameRoomService;
    }


    public void onPlayerInitRequest(InitPlayerEvent initEvent) {
        gameRoomService.getRoomByKey(initEvent.getSession().getRoomKey()).ifPresent(room -> room.onPlayerInitRequest(initEvent));
    }

    public void onPlayerKeyDown(KeyDownPlayerEvent event) {
        gameRoomService.getRoomByKey(event.getSession().getRoomKey()).ifPresent(room -> room.onPlayerKeyDown(event));
    }

    public void onPlayerMouseDown(MouseDownPlayerEvent event) {
        gameRoomService.getRoomByKey(event.getSession().getRoomKey()).ifPresent(room -> room.onPlayerMouseClick(event));
    }

    public void onPlayerMouseMove(MouseMovePlayerEvent event) {
        gameRoomService.getRoomByKey(event.getSession().getRoomKey()).ifPresent(room -> room.onPlayerMouseMove(event));
    }
}

