package com.tfkfan.nettywebgame.game.event;

import com.tfkfan.nettywebgame.networking.session.PlayerSession;

public interface Event {
    PlayerSession session();
    Event session(PlayerSession session);
}
