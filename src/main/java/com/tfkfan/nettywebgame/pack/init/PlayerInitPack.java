package com.tfkfan.nettywebgame.pack.init;

import com.tfkfan.nettywebgame.pack.InitPack;
import com.tfkfan.nettywebgame.shared.Vector;
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
