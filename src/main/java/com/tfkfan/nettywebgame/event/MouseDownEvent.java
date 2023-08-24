package com.tfkfan.nettywebgame.event;

import com.tfkfan.nettywebgame.shared.Vector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MouseDownEvent extends AbstractEvent {
    private String key;
    private Vector target;
}
