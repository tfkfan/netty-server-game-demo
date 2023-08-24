package com.tfkfan.nettywebgame.networking.message.impl;

import lombok.Data;

@Data
public class PlayerKeyDownMessage {
    private String inputId;
    private boolean state;
}
