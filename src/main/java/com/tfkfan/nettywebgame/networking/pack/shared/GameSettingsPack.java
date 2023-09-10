package com.tfkfan.nettywebgame.networking.pack.shared;

import com.tfkfan.nettywebgame.networking.pack.InitPack;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameSettingsPack implements InitPack {
    private Long loopRate;
}
