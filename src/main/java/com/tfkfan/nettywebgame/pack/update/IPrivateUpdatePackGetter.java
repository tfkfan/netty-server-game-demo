package com.tfkfan.nettywebgame.pack.update;

import com.tfkfan.nettywebgame.pack.PrivateUpdatePack;

public interface IPrivateUpdatePackGetter<T extends PrivateUpdatePack> {
    T getPrivateUpdatePack();
}
