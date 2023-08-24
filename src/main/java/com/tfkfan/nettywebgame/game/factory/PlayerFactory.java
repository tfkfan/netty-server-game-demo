package com.tfkfan.nettywebgame.game.factory;

import com.tfkfan.nettywebgame.game.model.Player;
import com.tfkfan.nettywebgame.game.room.GameRoom;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;

public interface PlayerFactory<ID, CM, P extends Player, GR extends GameRoom> {
    P create(ID nextId, CM initialData, GR gameRoom, PlayerSession playerSession);
}
