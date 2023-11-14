package com.tfkfan.nettywebgame.networking.adapter;

import com.google.gson.Gson;
import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.message.PlayerMessage;
import com.tfkfan.nettywebgame.networking.message.impl.incoming.IncomingMessage;
import com.tfkfan.nettywebgame.networking.message.impl.incoming.IncomingPlayerMessage;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Sharable
@Component
public class TextWebsocketDecoder extends MessageToMessageDecoder<TextWebSocketFrame> {
    private final Gson gson = new Gson();

    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame frame, List<Object> out) {
        final String json = frame.text();
        final PlayerSession ps = PlayerSession.getPlayerSessionFromChannel(ctx.channel());
        if (Objects.nonNull(ps)) {
            PlayerMessage playerMsg = gson.fromJson(json, IncomingPlayerMessage.class);
            playerMsg.setSession(ps);
            out.add(playerMsg);
        } else {
            Message msg = gson.fromJson(json, IncomingMessage.class);
            out.add(msg);
        }
    }
}
