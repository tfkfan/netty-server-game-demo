package com.tfkfan.nettywebgame.pack.shared;

import com.tfkfan.nettywebgame.pack.InitPack;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameSettingsPack implements InitPack {
    private Long loopRate;
}
