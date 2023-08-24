package com.tfkfan.nettywebgame.game.room;

import com.tfkfan.nettywebgame.config.ApplicationProperties;
import com.tfkfan.nettywebgame.game.factory.PlayerFactory;
import com.tfkfan.nettywebgame.game.map.GameMap;
import com.tfkfan.nettywebgame.game.model.DefaultPlayer;
import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.message.impl.ConnectMessage;
import com.tfkfan.nettywebgame.networking.message.impl.outcoming.OutcomingMessage;
import com.tfkfan.nettywebgame.networking.mode.MainGameChannelMode;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import com.tfkfan.nettywebgame.task.TaskManagerService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import static com.tfkfan.nettywebgame.shared.FrameUtil.eventToFrame;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameRoomManager {
    private final Map<UUID, DefaultGameRoom> gameRoomMap = new ConcurrentHashMap<>();
    private final Queue<WaitingPlayerSession> sessionQueue = new ConcurrentLinkedQueue<>();

    private final MainGameChannelMode gameChannelMode;
    private final PlayerFactory<Long, ConnectMessage, DefaultPlayer, DefaultGameRoom> playerFactory;
    private final ApplicationProperties applicationProperties;
    private final TaskManagerService schedulerService;
    public DefaultGameRoom getRoomByKey(UUID key) {
        return gameRoomMap.get(key);
    }

    public void addPlayerToWait(PlayerSession playerSession, ConnectMessage initialData) {
        try {
            sessionQueue.add(new WaitingPlayerSession(playerSession, initialData));
            playerSession.getChannel().writeAndFlush(eventToFrame(new OutcomingMessage(Message.CONNECT_WAIT)));

            if (sessionQueue.size() < applicationProperties.getRoom().getMaxplayers())
                return;

            final GameMap gameMap = new GameMap();
            final DefaultGameRoom room = new DefaultGameRoom(gameMap,
                    UUID.randomUUID(), GameRoomManager.this, schedulerService,  applicationProperties.getRoom());
            gameRoomMap.put(room.key(), room);

            final List<PlayerSession> playerSessions = new ArrayList<>();
            while (playerSessions.size() != applicationProperties.getRoom().getMaxplayers()) {
                final WaitingPlayerSession waitingPlayerSession = sessionQueue.remove();
                final PlayerSession ps = waitingPlayerSession.getPlayerSession();
                final DefaultPlayer player = playerFactory.create(gameMap.nextPlayerId(), waitingPlayerSession.getInitialData(),
                        room, ps);
                ps.setParentRoomKey(room.key());
                ps.setPlayer(player);
                playerSessions.add(ps);
            }
            gameChannelMode.apply(playerSessions);
            room.onRoomCreated(playerSessions);
            schedulerService.scheduleAtFixedRate(room, applicationProperties.getRoom().getInitdelay(), applicationProperties.getRoom().getLooprate(), TimeUnit.MILLISECONDS);
            room.onRoomStarted();
        } catch (Exception e) {
            log.info("Queue interrupted", e);
        }
    }

    @RequiredArgsConstructor
    @Setter
    @Getter
    static class WaitingPlayerSession {
        private final PlayerSession playerSession;
        private final ConnectMessage initialData;
    }
}
