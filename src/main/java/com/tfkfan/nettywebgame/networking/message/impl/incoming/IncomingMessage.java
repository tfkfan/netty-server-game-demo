package com.tfkfan.nettywebgame.networking.message.impl.incoming;


import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.message.impl.AbstractMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@Getter
@Setter
@NoArgsConstructor
public class IncomingMessage extends AbstractMessage implements Message {
    public IncomingMessage(int type, String data) {
        super(type, data);
    }
}
