package com.tfkfan.nettywebgame.networking.mode;

import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import com.tfkfan.nettywebgame.shared.ChannelUtil;
import io.netty.channel.ChannelPipeline;

import java.util.List;

public abstract class AbstractGameChannelMode implements GameChannelMode {
    final String modeName;

    public AbstractGameChannelMode(String modeName) {
        super();
        this.modeName = modeName;
    }

    @Override
    public String getModeName() {
        return modeName;
    }

    @Override
    public void apply(PlayerSession playerSession, boolean clearExistingProtocolHandlers) {
        if (clearExistingProtocolHandlers) {
            ChannelPipeline pipeline = ChannelUtil
                    .getPipeLineOfConnection(playerSession);
            ChannelUtil.clearPipeline(pipeline);
        }
        apply(playerSession);
    }

    @Override
    public void apply(List<PlayerSession> playerSessions) {
        playerSessions.forEach(this::apply);
    }

    @Override
    public void apply(List<PlayerSession> playerSessions, boolean clearExistingProtocolHandlers) {
        playerSessions.forEach(playerSession -> apply(playerSession, clearExistingProtocolHandlers));
    }
}
