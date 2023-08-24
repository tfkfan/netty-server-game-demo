package com.tfkfan.nettywebgame.shared;

import lombok.Getter;

/**
 * @author Baltser Artem tfkfan
 */
@Getter
public enum MessageType {
    SYSTEM(1), ROOM(2), PRIVATE(3), ADMIN(4);
    private final Integer type;

    MessageType(Integer type) {
        this.type = type;
    }

}
