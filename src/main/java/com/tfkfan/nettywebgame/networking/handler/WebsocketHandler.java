package com.tfkfan.nettywebgame.networking.handler;

import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.message.MessageType;
import com.tfkfan.nettywebgame.networking.message.impl.outcoming.OutcomingMessage;
import com.tfkfan.nettywebgame.networking.pack.shared.ExceptionPack;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

public interface WebsocketHandler {
    default ChannelFuture send(Channel channel, Message message) {
        return channel.writeAndFlush(message);
    }

    default ChannelFuture sendFailure(ChannelHandlerContext ctx, String message) {
        return send(ctx.channel(), new OutcomingMessage(MessageType.FAILURE, new ExceptionPack(message)));
    }
}
