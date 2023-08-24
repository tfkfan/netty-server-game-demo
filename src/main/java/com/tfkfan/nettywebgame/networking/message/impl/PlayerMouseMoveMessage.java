package com.tfkfan.nettywebgame.networking.message.impl;

import com.tfkfan.nettywebgame.shared.Vector;
import lombok.Data;

@Data
public class PlayerMouseMoveMessage {
    private Vector target;
}
