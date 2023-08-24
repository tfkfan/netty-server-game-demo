package com.tfkfan.nettywebgame.game.room;

import com.tfkfan.nettywebgame.event.Event;
import com.tfkfan.nettywebgame.event.listener.EventListener;
import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.message.PlayerMessage;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import com.tfkfan.nettywebgame.networking.session.Session;
import com.tfkfan.nettywebgame.task.Task;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public interface GameRoom extends Task {
    void onRoomCreated(List<PlayerSession> playerSessions);

    void onRoomStarted();

    void onBattleStarted();

    void onDestroy(List<PlayerSession> playerSessions);

    void onDisconnect(PlayerSession session);

    void update();

    <E extends Event>  void registerEventListener(Integer messageType, Class<E> eventType, EventListener<E> listener);
    void onMessage(PlayerMessage msg);

    Collection<PlayerSession> sessions();

    int currentPlayersCount();

    Optional<PlayerSession> getPlayerSessionBySessionId(Session session);

    UUID key();


    void send(PlayerSession playerSession, Message message);

    void sendBroadcast(Message message);

    void sendBroadcast(Function<PlayerSession, Message> function);

    void close();
}
