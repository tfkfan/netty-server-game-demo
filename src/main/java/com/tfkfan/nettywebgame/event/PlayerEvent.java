package com.tfkfan.nettywebgame.event;

import com.tfkfan.nettywebgame.networking.session.PlayerSession;

public interface PlayerEvent extends Event{
    PlayerSession getSession();
}
