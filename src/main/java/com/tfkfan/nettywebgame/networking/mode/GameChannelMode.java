package com.tfkfan.nettywebgame.networking.mode;

import com.tfkfan.nettywebgame.networking.session.Session;

import java.util.Collection;

public interface GameChannelMode {
    String GAME_CHANNEL_MODE = "GAME_CHANNEL_MODE";

    String getModeName();

    <T extends Session> void apply(T playerSession);

    <T extends Session> void apply(T playerSession,
               boolean clearExistingProtocolHandlers);

   <T extends Session> void apply(Collection<T> playerSessions);

    <T extends Session> void apply(Collection<T> playerSessions,
               boolean clearExistingProtocolHandlers);
}
