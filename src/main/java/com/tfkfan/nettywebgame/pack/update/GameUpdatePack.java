package com.tfkfan.nettywebgame.pack.update;

import com.tfkfan.nettywebgame.pack.UpdatePack;
import com.tfkfan.nettywebgame.pack.privat.PrivatePlayerUpdatePack;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class GameUpdatePack implements UpdatePack {
    private PrivatePlayerUpdatePack player;
    private Collection<PlayerUpdatePack> players;
}
