package com.tfkfan.nettywebgame.networking.mode;

import com.tfkfan.nettywebgame.networking.session.PlayerSession;

import java.util.List;

public interface GameChannelMode {
    String GAME_CHANNEL_MODE = "GAME_CHANNEL_MODE";

    String getModeName();

    void apply(PlayerSession playerSession);

    void apply(PlayerSession playerSession,
               boolean clearExistingProtocolHandlers);

    void apply(List<PlayerSession> playerSessions);

    void apply(List<PlayerSession> playerSessions,
               boolean clearExistingProtocolHandlers);
}
