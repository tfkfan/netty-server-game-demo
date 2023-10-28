package com.tfkfan.nettywebgame.service;

import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    public static final String TOKEN_FIELD = "BEARER_TOKEN";

    private final Map<String, String> mockTokensUsersData
            = Map.of("token1", "user1", "token2", "admin");

    public PlayerSession authenticate(Channel channel, String token) {
        if (!(mockTokensUsersData.containsKey(token)))
            throw new RuntimeException("auth error");
        return PlayerSession.createPlayerSession(channel, UUID.randomUUID());
    }
}
