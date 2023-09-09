package com.tfkfan.nettywebgame.networking.mode;

import com.tfkfan.nettywebgame.networking.session.Session;
import com.tfkfan.nettywebgame.server.adapter.TextWebsocketDecoder;
import com.tfkfan.nettywebgame.server.adapter.TextWebsocketEncoder;
import com.tfkfan.nettywebgame.server.handler.GameWebsocketHandler;
import com.tfkfan.nettywebgame.shared.ChannelUtil;
import com.tfkfan.nettywebgame.shared.ServerConstants;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MainGameChannelMode extends AbstractGameChannelMode {
    private final TextWebsocketDecoder textWebsocketDecoder;
    private final TextWebsocketEncoder textWebsocketEncoder;
    private final GameWebsocketHandler baseGameHandler;

    public MainGameChannelMode(TextWebsocketDecoder textWebsocketDecoder, TextWebsocketEncoder textWebsocketEncoder, GameWebsocketHandler baseGameHandler) {
        super(GAME_CHANNEL_MODE);
        this.textWebsocketDecoder = textWebsocketDecoder;
        this.textWebsocketEncoder = textWebsocketEncoder;
        this.baseGameHandler = baseGameHandler;
    }

    @Override
    public <T extends Session> void apply(T playerSession, boolean clearExistingProtocolHandlers) {
        apply(playerSession);
    }

    @Override
    public  <T extends Session> void apply(T playerSession) {
        log.trace("Going to apply {} on session: {}", getModeName(), playerSession);

        ChannelPipeline pipeline = ChannelUtil
                .getPipeLineOfConnection(playerSession);
        if (!pipeline.names().contains(ServerConstants.TXT_WS_DECODER))
            pipeline.addLast(ServerConstants.TXT_WS_DECODER, textWebsocketDecoder);
        if (!pipeline.names().contains(ServerConstants.TXT_WS_ENCODER))
            pipeline.addLast(ServerConstants.TXT_WS_ENCODER, textWebsocketEncoder);
        if (!pipeline.names().contains(ServerConstants.EVENT_HANDLER))
            pipeline.addLast(ServerConstants.EVENT_HANDLER, baseGameHandler);
        if (pipeline.names().contains(ServerConstants.PLAYER_START_HANDLER_NAME))
            pipeline.remove(ServerConstants.PLAYER_START_HANDLER_NAME);
        if (pipeline.names().contains(ServerConstants.INIT_HANDLER_NAME))
            pipeline.remove(ServerConstants.INIT_HANDLER_NAME);
    }
}
