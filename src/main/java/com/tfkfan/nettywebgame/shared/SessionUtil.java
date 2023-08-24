package com.tfkfan.nettywebgame.shared;

import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.util.UUID;

public class SessionUtil {
    private static final AttributeKey<PlayerSession> PLAYER_SESSION_ATTRIBUTE_KEY = AttributeKey.valueOf("plx_session");

    public static PlayerSession createPlayerSession(Channel channel, UUID uuid) {
        PlayerSession playerSession = new PlayerSession(uuid, channel);
        addPlayerSessionToChannel(channel, playerSession);
        return playerSession;
    }

    public static boolean addPlayerSessionToChannel(Channel channel, PlayerSession session) {
        Attribute<PlayerSession> sessionAttr = channel.attr(PLAYER_SESSION_ATTRIBUTE_KEY);
        return sessionAttr.compareAndSet(null, session);
    }

    public static PlayerSession getPlayerSessionFromChannel(ChannelHandlerContext ctx) {
        return getPlayerSessionFromChannel(ctx.channel());
    }

    public static PlayerSession getPlayerSessionFromChannel(Channel channel) {
        Attribute<PlayerSession> sessionAttr = channel.attr(PLAYER_SESSION_ATTRIBUTE_KEY);
        return sessionAttr.get();
    }

    public static void removePlayerSessionFromChannel(Channel channel) {
        Attribute<PlayerSession> sessionAttr = channel.attr(PLAYER_SESSION_ATTRIBUTE_KEY);
        sessionAttr.set(null);
    }

    public static void removePlayerSessionFromChannel(ChannelHandlerContext ctx) {
        removePlayerSessionFromChannel(ctx.channel());
    }
}
