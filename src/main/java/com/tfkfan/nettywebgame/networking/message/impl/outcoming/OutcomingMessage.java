package com.tfkfan.nettywebgame.networking.message.impl.outcoming;


import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.message.impl.AbstractMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class OutcomingMessage extends AbstractMessage implements Message, Serializable {
    public OutcomingMessage(int type) {
        super(type, null);
    }

    public OutcomingMessage(int type, Object data) {
        super(type, data);
    }
}
