package com.tfkfan.nettywebgame.networking.server.handler;

import com.tfkfan.nettywebgame.event.AuthEvent;
import com.tfkfan.nettywebgame.event.dispatcher.EventDispatcher;
import com.tfkfan.nettywebgame.networking.message.Message;
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
public class InitialHandler extends AbstractHandler<IncomingMessage> {
    private final OutOfRoomChannelMode outOfRoomChannelMode;
    private final AuthService authService;

    public InitialHandler(OutOfRoomChannelMode outOfRoomChannelMode, AuthService authService) {
        super(new EventDispatcher<>());
        this.outOfRoomChannelMode = outOfRoomChannelMode;
        this.authService = authService;

        addEventListener(Message.AUTHENTICATION, AuthEvent.class, this::handleAuthenticate);
    }

    public void handleAuthenticate(AuthEvent event) {
        outOfRoomChannelMode.apply(authService.authenticate(event.getChannel(), event.getBearerToken()));
        send(event.getChannel(), new OutcomingMessage(Message.AUTHENTICATION));
    }
}
