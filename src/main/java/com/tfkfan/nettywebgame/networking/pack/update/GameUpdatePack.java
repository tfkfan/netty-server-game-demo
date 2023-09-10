package com.tfkfan.nettywebgame.networking.pack.update;

import com.tfkfan.nettywebgame.networking.pack.UpdatePack;
import com.tfkfan.nettywebgame.networking.pack.privat.PrivatePlayerUpdatePack;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class GameUpdatePack implements UpdatePack {
    private PrivatePlayerUpdatePack player;
    private Collection<PlayerUpdatePack> players;
}
