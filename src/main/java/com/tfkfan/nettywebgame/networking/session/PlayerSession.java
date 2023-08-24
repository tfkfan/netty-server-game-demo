package com.tfkfan.nettywebgame.networking.session;

import com.tfkfan.nettywebgame.game.model.Player;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerSession extends Session{
    protected Player player;
    protected UUID parentRoomKey;

    public PlayerSession(UUID id, Channel channel) {
        super(id, channel);
    }

    @Override
    public String toString() {
        return "PlayerSession [id=" + getId() + "player=" + player
                + ", parentGameRoom=" + parentRoomKey + "]";
    }
}
