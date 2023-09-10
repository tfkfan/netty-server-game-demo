package com.tfkfan.nettywebgame.networking.pack.update;

import com.tfkfan.nettywebgame.networking.pack.PrivateUpdatePack;

public interface IPrivateUpdatePackGetter<T extends PrivateUpdatePack> {
    T getPrivateUpdatePack();
}
