package com.tfkfan.nettywebgame.networking.message.impl.incoming;

import com.tfkfan.nettywebgame.networking.message.PlayerMessage;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IncomingPlayerMessage extends IncomingMessage implements PlayerMessage {
    private transient PlayerSession playerSession;

    public IncomingPlayerMessage(PlayerSession playerSession, int type, String source) {
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
