package com.tfkfan.nettywebgame.event.listener;

import com.tfkfan.nettywebgame.event.Event;

@FunctionalInterface
public interface EventListener<T extends Event> {
    void onEvent(T event);

}
