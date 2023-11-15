package com.tfkfan.nettywebgame.game.room;

import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import com.tfkfan.nettywebgame.networking.session.Session;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;

public interface GameRoom extends Runnable {
    void start(long initialDelay, long startDelay, long endDelay, long loopRate);

    void onRoomCreated(List<PlayerSession> playerSessions);

    void onRoomStarted();

    void onBattleStarted();

    void onBattleEnd();

    void onDestroy(List<PlayerSession> playerSessions);

    void onDisconnect(PlayerSession session);

    void update();

    Collection<PlayerSession> sessions();

    int currentPlayersCount();

    Optional<PlayerSession> getPlayerSessionBySessionId(Session session);

    UUID key();


    void send(PlayerSession playerSession, Message message);

    void sendBroadcast(Message message);

    void sendBroadcast(Function<PlayerSession, Message> function);

    Collection<PlayerSession> close();

    ScheduledExecutorService getRoomExecutorService();
}
