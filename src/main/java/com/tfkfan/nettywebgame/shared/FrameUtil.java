package com.tfkfan.nettywebgame.shared;

import com.google.gson.Gson;
import com.tfkfan.nettywebgame.networking.message.Message;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class FrameUtil {
    private final static Gson objectMapper = new Gson();

    public static TextWebSocketFrame eventToFrame(Message message){
        return new TextWebSocketFrame(objectMapper.toJson(message));
    }
}
