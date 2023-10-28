package com.tfkfan.nettywebgame.networking.session;

import com.tfkfan.nettywebgame.game.model.Player;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerSession extends Session {
    private static final AttributeKey<PlayerSession> PLAYER_SESSION_ATTRIBUTE_KEY = AttributeKey.valueOf("plx_session");

    protected Player player;
    protected UUID roomKey;

    public static PlayerSession createPlayerSession(Channel channel, UUID uuid) {
        return addPlayerSessionToChannel(channel, new PlayerSession(uuid, channel));
    }

    public static PlayerSession addPlayerSessionToChannel(Channel channel, PlayerSession session) {
        Attribute<PlayerSession> sessionAttr = channel.attr(PLAYER_SESSION_ATTRIBUTE_KEY);
        sessionAttr.compareAndSet(null, session);
        return session;
    }

    public static PlayerSession getPlayerSessionFromChannel(ChannelHandlerContext ctx) {
        return getPlayerSessionFromChannel(ctx.channel());
    }

    public static PlayerSession getPlayerSessionFromChannel(Channel channel) {
        Attribute<PlayerSession> sessionAttr = channel.attr(PLAYER_SESSION_ATTRIBUTE_KEY);
        return sessionAttr.get();
    }

    private PlayerSession(UUID id, Channel channel) {
        super(id, channel);
    }
    public void removePlayerSessionFromChannel() {
        Attribute<PlayerSession> sessionAttr = channel.attr(PLAYER_SESSION_ATTRIBUTE_KEY);
        sessionAttr.set(null);
    }
    @Override
    public String toString() {
        return "PlayerSession [id=" + getId() + "player=" + player
                + ", parentGameRoom=" + roomKey + "]";
    }
}
