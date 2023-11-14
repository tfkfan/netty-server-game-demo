package com.tfkfan.nettywebgame.service;

import com.tfkfan.nettywebgame.config.ApplicationProperties;
import com.tfkfan.nettywebgame.event.GameRoomJoinEvent;
import com.tfkfan.nettywebgame.game.factory.PlayerFactory;
import com.tfkfan.nettywebgame.game.map.GameMap;
import com.tfkfan.nettywebgame.game.model.DefaultPlayer;
import com.tfkfan.nettywebgame.game.room.DefaultGameRoom;
import com.tfkfan.nettywebgame.networking.message.MessageType;
import com.tfkfan.nettywebgame.networking.message.impl.outcoming.OutcomingMessage;
import com.tfkfan.nettywebgame.networking.mode.MainGameChannelMode;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.tfkfan.nettywebgame.shared.FrameUtil.eventToFrame;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameRoomService {
    private final Map<UUID, DefaultGameRoom> gameRoomMap = new ConcurrentHashMap<>();
    private final Queue<GameRoomJoinEvent> sessionQueue = new ConcurrentLinkedQueue<>();

    private final MainGameChannelMode gameChannelMode;
    private final PlayerFactory<Long, GameRoomJoinEvent, DefaultPlayer, DefaultGameRoom> playerFactory;
    private final ApplicationProperties applicationProperties;
    private final ScheduledExecutorService schedulerService;

    public Optional<DefaultGameRoom> getRoomByKey(UUID key) {
        return Optional.ofNullable(gameRoomMap.get(key));
    }

    public void addPlayerToWait(GameRoomJoinEvent event) {
        try {
            sessionQueue.add(event);
            event.getSession().getChannel().writeAndFlush(eventToFrame(new OutcomingMessage(MessageType.CONNECT_WAIT)));

            if (sessionQueue.size() < applicationProperties.getRoom().getMaxplayers())
                return;

            final GameMap gameMap = new GameMap();
            final DefaultGameRoom room = new DefaultGameRoom(gameMap,
                    UUID.randomUUID(), GameRoomService.this, schedulerService, applicationProperties.getRoom());
            gameRoomMap.put(room.key(), room);

            final List<PlayerSession> playerSessions = new ArrayList<>();
            while (playerSessions.size() != applicationProperties.getRoom().getMaxplayers()) {
                final GameRoomJoinEvent evt = sessionQueue.remove();
                final PlayerSession ps = evt.getSession();
                final DefaultPlayer player = playerFactory.create(gameMap.nextPlayerId(), evt, room, ps);
                ps.setRoomKey(room.key());
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
}
