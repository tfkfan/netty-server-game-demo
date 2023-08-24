package com.tfkfan.nettywebgame.event;

import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DisconnectEvent extends AbstractEvent{
    public DisconnectEvent(PlayerSession session) {
        super(session);
    }
}
