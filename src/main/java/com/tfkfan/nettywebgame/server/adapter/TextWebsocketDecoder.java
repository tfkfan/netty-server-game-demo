package com.tfkfan.nettywebgame.server.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfkfan.nettywebgame.networking.message.*;
import com.tfkfan.nettywebgame.networking.message.impl.incoming.IncomingMessage;
import com.tfkfan.nettywebgame.networking.message.impl.incoming.IncomingPlayerMessage;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import com.tfkfan.nettywebgame.shared.SessionUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.List;

@Sharable
@Component
public class TextWebsocketDecoder extends MessageToMessageDecoder<TextWebSocketFrame> {
    private final ObjectMapper jackson = new ObjectMapper();

    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame frame,
                          List<Object> out) throws Exception {
        final String json = frame.text();

        PlayerSession ps = SessionUtil.getPlayerSessionFromChannel(ctx.channel());
        if (null != ps) {
            PlayerMessage playerMsg = jackson.readValue(json, IncomingPlayerMessage.class);
            playerMsg.setSession(ps);
            out.add(playerMsg);
        } else {
            Message msg = jackson.readValue(json, IncomingMessage.class);
            out.add(msg);
        }
    }
}
