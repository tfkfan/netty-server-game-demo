package com.tfkfan.nettywebgame.event;

import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import lombok.Getter;

@Getter
public class KeyDownPlayerEvent extends AbstractPlayerEvent {
    private final String inputId;
    private final Boolean state;

    public KeyDownPlayerEvent(PlayerSession session, String inputId, Boolean state) {
        super(session);
        this.inputId = inputId;
        this.state = state;
    }
}
