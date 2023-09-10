package com.tfkfan.nettywebgame.networking.pack.update;

import com.tfkfan.nettywebgame.networking.pack.UpdatePack;

public interface IUpdatePackGetter<T extends UpdatePack> {
    T getUpdatePack();
}
