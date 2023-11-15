package com.tfkfan.nettywebgame.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.NoSuchElementException;

@Slf4j
public class ChannelUtil {

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
}
