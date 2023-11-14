package com.tfkfan.nettywebgame.game.room;

import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.message.MessageType;
import com.tfkfan.nettywebgame.networking.message.impl.outcoming.OutcomingMessage;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import com.tfkfan.nettywebgame.networking.session.Session;
import com.tfkfan.nettywebgame.service.GameRoomService;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Slf4j
public abstract class AbstractGameRoom implements GameRoom {
    private final UUID gameRoomId;
    protected Map<UUID, PlayerSession> sessions = new ConcurrentHashMap<>();
    protected GameRoomService gameRoomService;

    protected AbstractGameRoom(UUID gameRoomId, GameRoomService gameRoomService) {
        this.gameRoomId = gameRoomId;
        this.gameRoomService = gameRoomService;
    }

    @Override
    public void onRoomCreated(List<PlayerSession> playerSessions) {
        for (PlayerSession playerSession : playerSessions) {
            sessions.put(playerSession.getId(), playerSession);
            sendBroadcast(new OutcomingMessage(MessageType.MESSAGE,
                    playerSession.getPlayer().getId() + " successfully joined"));
        }
    }

    @Override
    public void onDestroy(List<PlayerSession> playerSessions) {
        for (PlayerSession playerSession : playerSessions) {
            sessions.remove(playerSession.getId());
            sendBroadcast(new OutcomingMessage(MessageType.MESSAGE,
                    playerSession.getPlayer().getId() + " left"));
        }
    }

    public void onDisconnect(PlayerSession playerSession) {
        sessions.remove(playerSession.getId());
        playerSession.removePlayerSessionFromChannel();
    }

    @Override
    public void send(PlayerSession playerSession, Message message) {
        if (playerSession != null)
            playerSession.getChannel().writeAndFlush(message);
    }

    @Override
    public void sendBroadcast(Message networkMessage) {
        sessions.values().forEach(s -> s.getChannel().writeAndFlush(networkMessage));
    }

    @Override
    public void sendBroadcast(Function<PlayerSession, Message> function) {
        sessions.values().forEach(s -> s.getChannel().writeAndFlush(function.apply(s)));
    }

    @Override
    public void run() {
        try {
            update();
        } catch (Exception e) {
            log.error("room update exception", e);
        }
    }

    @Override
    public synchronized void close() {
        sessions.values().forEach(this::onDisconnect);
    }

    @Override
    public Optional<PlayerSession> getPlayerSessionBySessionId(Session session) {
        if (sessions.containsKey(session.getId()))
            return Optional.of(sessions.get(session.getId()));
        return Optional.empty();
    }

    @Override
    public Collection<PlayerSession> sessions() {
        return sessions.values();
    }

    @Override
    public int currentPlayersCount() {
        return sessions.size();
    }

    @Override
    public UUID key() {
        return gameRoomId;
    }
}
