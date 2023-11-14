package com.tfkfan.nettywebgame.networking.pack;

import com.tfkfan.nettywebgame.networking.pack.InitPack;

public interface IInitPackSupplier<T extends InitPack> {
    T getInitPack();
}
