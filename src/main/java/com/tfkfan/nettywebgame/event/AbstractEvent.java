package com.tfkfan.nettywebgame.event;

import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractEvent implements Event {
    private  PlayerSession session;
    @Override
    public PlayerSession session() {
        return session;
    }

    @Override
    public Event session(PlayerSession session) {
        this.session = session;
        return this;
    }
}
