package com.tfkfan.nettywebgame.networking.message.impl.outcoming;


import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.message.impl.AbstractMessage;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class OutcomingMessage extends AbstractMessage implements Message, Serializable {
    @Serial
    private static final long serialVersionUID = 8188757584720622237L;

    public OutcomingMessage() {
    }

    public OutcomingMessage(int type) {
        super(type, null);
    }

    public OutcomingMessage(int type, Object data) {
        super(type, data);
    }
}
