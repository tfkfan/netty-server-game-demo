package com.tfkfan.nettywebgame.networking.message;

import com.tfkfan.nettywebgame.networking.session.PlayerSession;

public interface PlayerMessage extends Message {
    PlayerSession session();

    void setSession(PlayerSession session);
}
