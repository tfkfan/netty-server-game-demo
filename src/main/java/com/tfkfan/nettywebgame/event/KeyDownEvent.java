package com.tfkfan.nettywebgame.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyDownEvent extends AbstractEvent{
    private String inputId;
    private Boolean state;
}
