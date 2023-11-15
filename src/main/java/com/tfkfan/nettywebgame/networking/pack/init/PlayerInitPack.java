package com.tfkfan.nettywebgame.networking.pack.init;

import com.tfkfan.nettywebgame.networking.pack.InitPack;
import com.tfkfan.nettywebgame.game.model.Vector;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerInitPack implements InitPack {
    private Long id;
    private Vector position;
}
