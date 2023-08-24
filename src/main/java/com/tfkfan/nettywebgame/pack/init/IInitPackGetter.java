package com.tfkfan.nettywebgame.pack.init;

import com.tfkfan.nettywebgame.pack.InitPack;

public interface IInitPackGetter<T extends InitPack> {
    T getInitPack();
}
