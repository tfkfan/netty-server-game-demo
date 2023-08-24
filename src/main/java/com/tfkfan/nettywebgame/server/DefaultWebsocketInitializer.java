package com.tfkfan.nettywebgame.server;

import com.tfkfan.nettywebgame.server.handler.InitialWebsocketHandler;
import com.tfkfan.nettywebgame.shared.ServerConstants;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.springframework.stereotype.Component;

@Component
public class DefaultWebsocketInitializer extends ChannelInitializer<Channel> {
    private final InitialWebsocketHandler webSocketHandlerMain;

    public DefaultWebsocketInitializer(InitialWebsocketHandler webSocketHandlerMain) {
        this.webSocketHandlerMain = webSocketHandlerMain;
    }

    @Override
    protected void initChannel(Channel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(ServerConstants.DEFAULT_OBJECT_AGGREGATOR_CONTENT_LENGTH));
        pipeline.addLast("handler", new WebSocketServerProtocolHandler(ServerConstants.WEBSOCKET_PATH));
        pipeline.addLast(ServerConstants.INIT_HANDLER_NAME, webSocketHandlerMain);
        pipeline.addLast("encoder", new HttpResponseEncoder());
    }
}
