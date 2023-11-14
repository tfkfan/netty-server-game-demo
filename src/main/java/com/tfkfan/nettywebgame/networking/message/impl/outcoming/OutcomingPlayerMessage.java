package com.tfkfan.nettywebgame.networking.message.impl.outcoming;

import com.tfkfan.nettywebgame.networking.message.PlayerMessage;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutcomingPlayerMessage extends OutcomingMessage implements PlayerMessage {
    private transient PlayerSession playerSession;

    public OutcomingPlayerMessage(int type) {
        super(type, null);
    }

    public OutcomingPlayerMessage(PlayerSession playerSession, int type, Object source) {
        super(type, source);
        this.playerSession = playerSession;
    }

    @Override
    public PlayerSession session() {
        return playerSession;
    }

    @Override
    public void setSession(PlayerSession session) {
        this.playerSession = session;
    }
}
