package com.tfkfan.nettywebgame.networking.pack;

public interface IPrivateUpdatePackSupplier<T extends PrivateUpdatePack> {
    T getPrivateUpdatePack();
}
