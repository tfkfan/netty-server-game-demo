package com.tfkfan.nettywebgame.networking.pack.update;

import com.tfkfan.nettywebgame.networking.pack.UpdatePack;
import com.tfkfan.nettywebgame.game.model.Vector;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerUpdatePack implements UpdatePack {
    private Long id;
    private Vector position;
}
