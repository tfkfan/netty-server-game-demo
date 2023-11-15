package com.tfkfan.nettywebgame.networking.mode;

import com.tfkfan.nettywebgame.networking.adapter.TextWebsocketDecoder;
import com.tfkfan.nettywebgame.networking.handler.InitialGameHandler;
import com.tfkfan.nettywebgame.networking.handler.PingPongWebsocketHandler;
import com.tfkfan.nettywebgame.config.ServerConstants;
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
    private final InitialGameHandler webSocketHandlerMain;
    private final PingPongWebsocketHandler pingPongWebsocketHandler;
    private final TextWebsocketDecoder textWebsocketDecoder;

    public DefaultWebsocketInitializer(InitialGameHandler webSocketHandlerMain, PingPongWebsocketHandler pingPongWebsocketHandler, TextWebsocketDecoder textWebsocketDecoder) {
        this.webSocketHandlerMain = webSocketHandlerMain;
        this.pingPongWebsocketHandler = pingPongWebsocketHandler;
        this.textWebsocketDecoder = textWebsocketDecoder;
    }

    @Override
    protected void initChannel(Channel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(ServerConstants.DEFAULT_OBJECT_AGGREGATOR_CONTENT_LENGTH));
        pipeline.addLast("handler", new WebSocketServerProtocolHandler(ServerConstants.WEBSOCKET_PATH));
        pipeline.addLast(ServerConstants.PING_PONG_HANDLER_NAME, pingPongWebsocketHandler);
        pipeline.addLast(ServerConstants.TXT_WS_DECODER, textWebsocketDecoder);
        pipeline.addLast(ServerConstants.INIT_HANDLER_NAME, webSocketHandlerMain);
        pipeline.addLast("encoder", new HttpResponseEncoder());
    }
}
