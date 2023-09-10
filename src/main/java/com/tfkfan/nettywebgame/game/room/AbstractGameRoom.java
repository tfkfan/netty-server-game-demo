package com.tfkfan.nettywebgame.game.room;

import com.google.gson.Gson;
import com.tfkfan.nettywebgame.game.event.Event;
import com.tfkfan.nettywebgame.game.event.listener.EventListener;
import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.message.PlayerMessage;
import com.tfkfan.nettywebgame.networking.message.impl.outcoming.OutcomingMessage;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import com.tfkfan.nettywebgame.networking.session.Session;
import com.tfkfan.nettywebgame.shared.Constants;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Slf4j
public abstract class AbstractGameRoom implements GameRoom {
    private final Gson objectMapper = new Gson();
    private final UUID gameRoomId;
    protected Map<UUID, PlayerSession> sessions = new ConcurrentHashMap<>();
    protected GameRoomManager gameRoomManager;

    protected final Map<Integer, Class<? extends Event>> typeToMappingEventClassMap = new ConcurrentHashMap<>();
    protected final ConcurrentHashMap<Class<? extends Event>,
            EventListener> eventListeners = new ConcurrentHashMap<>();

    protected AbstractGameRoom(UUID gameRoomId, GameRoomManager gameRoomManager) {
        this.gameRoomId = gameRoomId;
        this.gameRoomManager = gameRoomManager;
    }

    @Override
    public void onRoomCreated(List<PlayerSession> playerSessions) {
        for (PlayerSession playerSession : playerSessions) {
            sessions.put(playerSession.getId(), playerSession);
            sendBroadcast(new OutcomingMessage(Message.MESSAGE,
                    playerSession.getPlayer().getId() + " successfully joined"));
        }
    }

    @Override
    public void onDestroy(List<PlayerSession> playerSessions) {
        for (PlayerSession playerSession : playerSessions) {
            sessions.remove(playerSession.getId());
            sendBroadcast(new OutcomingMessage(Message.MESSAGE,
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
    public <E extends Event> void registerEventListener(Integer messageType, Class<E> eventType, EventListener<E> listener) {
        typeToMappingEventClassMap.put(messageType, eventType);
        eventListeners.put(eventType, listener);
    }

    @Override
    public void onMessage(PlayerMessage msg) {
        Class<? extends Event> eventClass = typeToMappingEventClassMap.get(msg.getType());
        if (eventClass == null || msg.getSession() == null)
            return;

        Event event = objectMapper.fromJson(msg.getData() != null ?
                        msg.getData().toString() : Constants.EMPTY_JSON, eventClass)
                .session(msg.getSession());
        if (eventListeners.containsKey(eventClass))
            eventListeners.get(eventClass).onEvent(event);
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
