package com.tfkfan.nettywebgame.networking.message.impl;


import com.tfkfan.nettywebgame.networking.message.Message;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public  abstract class AbstractMessage implements Message, Serializable {
    @Serial
    private static final long serialVersionUID = 8188757584720622237L;

    protected int type;
    protected Object data;

    public AbstractMessage() {
    }

    public AbstractMessage(int type, Object data) {
        this.type = type;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Event [type=" + type + ", source=" + data.toString() + "]";
    }
}
