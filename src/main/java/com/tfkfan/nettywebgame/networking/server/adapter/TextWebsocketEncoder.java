package com.tfkfan.nettywebgame.networking.server.adapter;

import com.google.gson.Gson;
import com.tfkfan.nettywebgame.networking.message.Message;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.List;

@Sharable
@Component
public class TextWebsocketEncoder extends MessageToMessageEncoder<Message> {
    private final Gson gson = new Gson();

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) {
        out.add(new TextWebSocketFrame(gson.toJson(msg)));
    }
}
