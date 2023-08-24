package com.tfkfan.nettywebgame.networking.message.impl.incoming;

import com.tfkfan.nettywebgame.networking.message.PlayerMessage;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomingPlayerMessage extends IncomingMessage implements PlayerMessage {
    private Long selfId;
    private transient PlayerSession playerSession;

    public IncomingPlayerMessage() {
    }

    public IncomingPlayerMessage(PlayerSession playerSession, int type, String source) {
        this(playerSession, type, source, null);
    }

    public IncomingPlayerMessage(PlayerSession playerSession, int type, String source, Long selfId) {
        super(type, source);
        this.playerSession = playerSession;
        this.selfId = selfId;
    }

    @Override
    public PlayerSession getSession() {
        return playerSession;
    }

    @Override
    public void setSession(PlayerSession session) {
        this.playerSession = session;
    }
}
