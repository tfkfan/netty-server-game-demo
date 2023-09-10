package com.tfkfan.nettywebgame.networking.pack.init;

import com.tfkfan.nettywebgame.networking.pack.InitPack;

public interface IInitPackGetter<T extends InitPack> {
    T getInitPack();
}
