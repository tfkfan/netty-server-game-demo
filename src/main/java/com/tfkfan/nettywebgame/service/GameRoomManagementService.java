package com.tfkfan.nettywebgame.service;

import com.tfkfan.nettywebgame.config.ApplicationProperties;
import com.tfkfan.nettywebgame.event.GameRoomJoinEvent;
import com.tfkfan.nettywebgame.game.map.GameMap;
import com.tfkfan.nettywebgame.game.model.DefaultPlayer;
import com.tfkfan.nettywebgame.game.model.Player;
import com.tfkfan.nettywebgame.game.room.DefaultGameRoom;
import com.tfkfan.nettywebgame.game.room.GameRoom;
import com.tfkfan.nettywebgame.networking.handler.WebsocketHandler;
import com.tfkfan.nettywebgame.networking.message.MessageType;
import com.tfkfan.nettywebgame.networking.message.impl.outcoming.OutcomingMessage;
import com.tfkfan.nettywebgame.networking.mode.MainGameChannelMode;
import com.tfkfan.nettywebgame.networking.mode.OutOfRoomChannelMode;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameRoomManagementService implements WebsocketHandler {
    private final Map<UUID, DefaultGameRoom> gameRoomMap = new ConcurrentHashMap<>();
    private final Queue<GameRoomJoinEvent> sessionQueue = new ConcurrentLinkedQueue<>();

    private final MainGameChannelMode gameChannelMode;
    private final OutOfRoomChannelMode outOfRoomChannelMode;
    private final Player.PlayerFactory<GameRoomJoinEvent, DefaultPlayer, DefaultGameRoom> playerFactory;
    private final ApplicationProperties applicationProperties;
    private final ScheduledExecutorService schedulerService;

    public Optional<DefaultGameRoom> getRoomByKey(UUID key) {
        return Optional.ofNullable(gameRoomMap.get(key));
    }

    public void addPlayerToWait(GameRoomJoinEvent event) {
        sessionQueue.add(event);
        send(event, new OutcomingMessage(MessageType.JOIN_WAIT));

        if (sessionQueue.size() < applicationProperties.getRoom().getMaxPlayers())
            return;

        final GameMap gameMap = new GameMap();
        final DefaultGameRoom room = new DefaultGameRoom(gameMap,
                UUID.randomUUID(), GameRoomManagementService.this, schedulerService, applicationProperties.getRoom());
        gameRoomMap.put(room.key(), room);

        final List<PlayerSession> playerSessions = new ArrayList<>();
        while (playerSessions.size() != applicationProperties.getRoom().getMaxPlayers()) {
            final GameRoomJoinEvent evt = sessionQueue.remove();
            final PlayerSession ps = evt.getSession();
            ps.setRoomKey(room.key());
            ps.setPlayer(playerFactory.create(gameMap.nextPlayerId(), evt, room, ps));
            playerSessions.add(ps);
        }

        gameChannelMode.apply(playerSessions);
        room.onRoomCreated(playerSessions);
        room.start(applicationProperties.getRoom().getStartDelay(),
                applicationProperties.getRoom().getEndDelay(),
                applicationProperties.getRoom().getLoopRate());
    }

    public void onBattleEnd(GameRoom room) {
        gameRoomMap.remove(room.key());
        outOfRoomChannelMode.apply(room.close());
    }

    public void removePlayerFromWaitQueue(PlayerSession session) {
        sessionQueue.removeIf(event -> event.getSession().equals(session));
    }
}
