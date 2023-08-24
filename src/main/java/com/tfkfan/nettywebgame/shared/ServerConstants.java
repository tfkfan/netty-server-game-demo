package com.tfkfan.nettywebgame.shared;

public interface ServerConstants {
    String WEBSOCKET_PATH = "/websocket";

    String TXT_WS_DECODER = "textWebsocketDecoder";
    String TXT_WS_ENCODER = "textWebsocketEncoder";
    String INIT_HANDLER_NAME = "initialHandler";
    String PLAYER_START_HANDLER_NAME = "startHandler";
    String EVENT_HANDLER = "eventHandler";

    int DEFAULT_OBJECT_AGGREGATOR_CONTENT_LENGTH = 65536;
}
