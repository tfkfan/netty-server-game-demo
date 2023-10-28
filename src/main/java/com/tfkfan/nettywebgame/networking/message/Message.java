package com.tfkfan.nettywebgame.networking.message;

import java.io.Serializable;

public interface Message extends Serializable {
	int MESSAGE = 1;

	int CONNECT = 2;

	int CONNECT_FAILURE = 3;

	int CONNECT_WAIT = 4;
	int CONNECT_SUCCESS = 5;
	int AUTHENTICATION = 6;
	int INIT = 100;
	int BATTLE_START = 101;
	int ROOM_START = 102;
	int UPDATE = 103;
	int PLAYER_KEY_DOWN = 200;
	int PLAYER_MOUSE_DOWN = 201;
	int PLAYER_MOUSE_MOVE = 202;

	int FAILURE = 300;

	int getType();

	void setType(int type);

	Object getData();

	void setData(Object data);
}
