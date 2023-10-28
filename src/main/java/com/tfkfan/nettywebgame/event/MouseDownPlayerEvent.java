package com.tfkfan.nettywebgame.event;

import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import com.tfkfan.nettywebgame.shared.Vector;
import lombok.Getter;

@Getter
public class MouseDownPlayerEvent extends AbstractPlayerEvent {
    private String key;
    private Vector target;

    public MouseDownPlayerEvent(PlayerSession session, String key, Vector target) {
        super(session);
        this.key = key;
        this.target = target;
    }
}
