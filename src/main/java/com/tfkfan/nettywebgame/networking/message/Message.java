package com.tfkfan.nettywebgame.networking.message;

import java.io.Serializable;

public interface Message extends Serializable {

	int getType();

	void setType(int type);

	Object getData();

	void setData(Object data);
}
