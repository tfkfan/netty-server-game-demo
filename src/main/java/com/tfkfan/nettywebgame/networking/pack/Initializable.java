package com.tfkfan.nettywebgame.networking.pack;

public interface Initializable<T extends InitPack> {
    T init();
}
