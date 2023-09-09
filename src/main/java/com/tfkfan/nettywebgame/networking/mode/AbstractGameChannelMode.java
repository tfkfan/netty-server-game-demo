package com.tfkfan.nettywebgame.networking.mode;

import com.tfkfan.nettywebgame.networking.session.Session;
import com.tfkfan.nettywebgame.shared.ChannelUtil;
import io.netty.channel.ChannelPipeline;

import java.util.Collection;

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
    public  <T extends Session> void apply(T playerSession, boolean clearExistingProtocolHandlers) {
        if (clearExistingProtocolHandlers) {
            ChannelPipeline pipeline = ChannelUtil
                    .getPipeLineOfConnection(playerSession);
            ChannelUtil.clearPipeline(pipeline);
        }
        apply(playerSession);
    }

    @Override
    public  <T extends Session> void apply(Collection<T> playerSessions) {
        playerSessions.forEach(this::apply);
    }

    @Override
    public  <T extends Session> void apply(Collection<T> playerSessions, boolean clearExistingProtocolHandlers) {
        playerSessions.forEach(playerSession -> apply(playerSession, clearExistingProtocolHandlers));
    }
}
