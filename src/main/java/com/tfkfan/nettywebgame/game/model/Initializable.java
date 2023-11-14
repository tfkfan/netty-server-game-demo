package com.tfkfan.nettywebgame.game.model;

import com.tfkfan.nettywebgame.networking.pack.InitPack;

public interface Initializable<T extends InitPack> {
    T init();
}
