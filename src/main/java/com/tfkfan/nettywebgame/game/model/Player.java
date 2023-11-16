package com.tfkfan.nettywebgame.game.model;

import com.tfkfan.nettywebgame.game.room.GameRoom;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;

public interface Player extends Entity<Long> {
    interface PlayerFactory<CM, P extends Player, GR extends GameRoom> {
        P create(Long nextId, CM initialData, GR gameRoom, PlayerSession playerSession);
    }
}
