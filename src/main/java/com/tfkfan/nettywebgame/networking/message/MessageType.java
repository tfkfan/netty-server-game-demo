package com.tfkfan.nettywebgame.networking.message;

public interface MessageType {
    int MESSAGE = 1;

    int JOIN = 2;

    int JOIN_FAILURE = 3;

    int JOIN_WAIT = 4;
    int JOIN_SUCCESS = 5;
    int AUTHENTICATION = 6;
    int INIT = 100;
    int BATTLE_START = 101;
    int ROOM_START = 102;

    int ROOM_CLOSE = 103;
    int UPDATE = 104;
    int PLAYER_KEY_DOWN = 200;

    int FAILURE = 300;
}
