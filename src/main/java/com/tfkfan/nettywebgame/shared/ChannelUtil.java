package com.tfkfan.nettywebgame.shared;

import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.NoSuchElementException;

@Slf4j
public class ChannelUtil {
    public static ChannelPipeline getPipeLineOfConnection(
            PlayerSession playerSession) {
        return playerSession.getChannel().pipeline();
    }

    public static void clearPipeline(ChannelPipeline pipeline) {
        if (null == pipeline)
            return;

        try {
            int counter = 0;
            while (pipeline.first() != null) {
                pipeline.removeFirst();
                counter++;
            }
            log.trace("Removed {} handlers from pipeline", counter);
        } catch (NoSuchElementException e) {
            // all elements removed.
        }
    }

    public static String getChannelIP(Channel channel) {
        return ((InetSocketAddress) channel.remoteAddress()).getAddress().toString().substring(1);
    }

    public static ChannelFuture closeChannel(Channel channel) {
        return channel.close();
    }

    public static ChannelFuture closeChannel(ChannelHandlerContext ctx) {
        return closeChannel(ctx.channel());
    }

    public static ChannelFuture writeAndFlush(Channel channel, Object msg) {
        return channel.writeAndFlush(msg);
    }

    public static ChannelFuture writeAndFlush(ChannelHandlerContext ctx, Object msg) {
        return ctx.writeAndFlush(msg);
    }

}
