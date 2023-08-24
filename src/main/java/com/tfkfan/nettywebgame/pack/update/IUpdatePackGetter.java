package com.tfkfan.nettywebgame.pack.update;

import com.tfkfan.nettywebgame.pack.UpdatePack;

public interface IUpdatePackGetter<T extends UpdatePack> {
    T getUpdatePack();
}
