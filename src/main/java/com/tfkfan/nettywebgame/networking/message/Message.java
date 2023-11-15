package com.tfkfan.nettywebgame.networking.message;

import com.google.gson.Gson;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.io.Serializable;

public interface Message extends Serializable {

	int getType();

	void setType(int type);

	Object getData();

	void setData(Object data);
}
