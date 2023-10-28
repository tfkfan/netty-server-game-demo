package com.tfkfan.nettywebgame.event;

import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import lombok.Getter;

@Getter
public class InitPlayerEvent extends AbstractPlayerEvent {

    public InitPlayerEvent(PlayerSession session ) {
        super(session);
    }
}
