package com.tfkfan.nettywebgame.game.event.listener;

import com.tfkfan.nettywebgame.game.event.Event;

public interface EventListener<T extends Event> {
    void onEvent(T event);

}
