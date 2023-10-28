package com.tfkfan.nettywebgame.shared;

import com.tfkfan.nettywebgame.event.GameRoomJoinEvent;
import com.tfkfan.nettywebgame.networking.message.impl.ConnectMessage;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class WaitingPlayerSession {
    private final PlayerSession playerSession;
    private final GameRoomJoinEvent initialData;
}
