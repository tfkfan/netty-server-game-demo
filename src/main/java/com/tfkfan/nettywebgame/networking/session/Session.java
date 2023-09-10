package com.tfkfan.nettywebgame.networking.session;

import com.tfkfan.nettywebgame.game.model.AbstractEntity;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Accessors(chain = true)
@Getter
@Setter
public abstract class Session extends AbstractEntity<UUID> {
    protected final Channel channel;
    protected final Map<String, Object> attributes = new ConcurrentHashMap<>();
    protected String ip;

    public Session(UUID id, Channel channel) {
        super(id);
        this.channel = channel;
    }

    public void setAttr(String key, Object value) {
        attributes.put(key, value);
    }

    public Object removeAttr(String key) {
        return attributes.remove(key);
    }
}
