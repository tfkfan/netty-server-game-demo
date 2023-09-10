package com.tfkfan.nettywebgame.game.event;

import com.tfkfan.nettywebgame.shared.Vector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MouseMoveEvent extends AbstractEvent {
    private Vector target;
}