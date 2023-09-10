package com.tfkfan.nettywebgame.networking.pack.init;

import com.tfkfan.nettywebgame.networking.pack.InitPack;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameInitPack implements InitPack {
    private PlayerInitPack player;
    private Long loopRate;
    private Long playersCount;
}
