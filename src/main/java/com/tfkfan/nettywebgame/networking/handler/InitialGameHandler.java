package com.tfkfan.nettywebgame.networking.handler;

import com.tfkfan.nettywebgame.event.AuthEvent;
import com.tfkfan.nettywebgame.event.dispatcher.EventDispatcher;
import com.tfkfan.nettywebgame.networking.message.MessageType;
import com.tfkfan.nettywebgame.networking.message.impl.incoming.IncomingMessage;
import com.tfkfan.nettywebgame.networking.message.impl.outcoming.OutcomingMessage;
import com.tfkfan.nettywebgame.networking.mode.OutOfRoomChannelMode;
import com.tfkfan.nettywebgame.service.AuthService;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@ChannelHandler.Sharable
@Component
public class InitialGameHandler extends AbstractGameHandler<IncomingMessage> {
    public InitialGameHandler(OutOfRoomChannelMode outOfRoomChannelMode, AuthService authService) {
        super(new EventDispatcher<>());

        addEventListener(MessageType.AUTHENTICATION, AuthEvent.class, event -> {
            outOfRoomChannelMode.apply(authService.authenticate(event.getChannel(), event.getBearerToken()));
            send(event.getChannel(), new OutcomingMessage(MessageType.AUTHENTICATION));
        });
    }
}
