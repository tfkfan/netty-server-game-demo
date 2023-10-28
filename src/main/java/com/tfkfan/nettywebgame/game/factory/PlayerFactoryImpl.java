package com.tfkfan.nettywebgame.game.factory;

import com.tfkfan.nettywebgame.event.GameRoomJoinEvent;
import com.tfkfan.nettywebgame.game.model.DefaultPlayer;
import com.tfkfan.nettywebgame.game.room.DefaultGameRoom;
import com.tfkfan.nettywebgame.networking.message.impl.ConnectMessage;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import org.springframework.stereotype.Component;

@Component
public class PlayerFactoryImpl implements PlayerFactory<Long, GameRoomJoinEvent,
        DefaultPlayer, DefaultGameRoom> {
    @Override
    public DefaultPlayer create(Long nextId, GameRoomJoinEvent initialData, DefaultGameRoom gameRoom, PlayerSession playerSession) {
        return new DefaultPlayer(nextId, gameRoom, playerSession);
    }
}
