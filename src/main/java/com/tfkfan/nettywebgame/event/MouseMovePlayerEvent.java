package com.tfkfan.nettywebgame.event;

import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import com.tfkfan.nettywebgame.shared.Vector;
import lombok.Getter;

@Getter
public class MouseMovePlayerEvent extends AbstractPlayerEvent {
    private Vector target;

    public MouseMovePlayerEvent(PlayerSession session, Vector target) {
        super(session);
        this.target = target;
    }
}
