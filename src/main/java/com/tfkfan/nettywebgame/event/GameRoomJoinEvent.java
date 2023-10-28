package com.tfkfan.nettywebgame.event;

import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameRoomJoinEvent extends AbstractPlayerEvent {
    public GameRoomJoinEvent(PlayerSession session) {
        super(session);
    }
}
