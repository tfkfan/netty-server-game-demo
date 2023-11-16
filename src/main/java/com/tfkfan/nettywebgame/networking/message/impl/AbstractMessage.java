package com.tfkfan.nettywebgame.networking.message.impl;


import com.tfkfan.nettywebgame.networking.message.Message;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public  abstract class AbstractMessage implements Message, Serializable {
    protected int type;
    protected Object data;
}
