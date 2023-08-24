package com.tfkfan.nettywebgame.event;

import com.tfkfan.nettywebgame.networking.session.PlayerSession;

public interface Event {
    PlayerSession session();
    Event session(PlayerSession session);
}
