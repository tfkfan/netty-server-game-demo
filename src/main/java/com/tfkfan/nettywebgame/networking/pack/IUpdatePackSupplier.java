package com.tfkfan.nettywebgame.networking.pack;

public interface IUpdatePackSupplier<T extends UpdatePack> {
    T getUpdatePack();
}
