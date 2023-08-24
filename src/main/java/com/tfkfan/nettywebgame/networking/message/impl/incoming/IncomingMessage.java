package com.tfkfan.nettywebgame.networking.message.impl.incoming;


import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.message.impl.AbstractMessage;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class IncomingMessage extends AbstractMessage implements Message {
    @Serial
    private static final long serialVersionUID = 8188757584720622237L;

    public IncomingMessage() {
    }
    public IncomingMessage(int type, String data) {
        super(type, data);
    }
}
