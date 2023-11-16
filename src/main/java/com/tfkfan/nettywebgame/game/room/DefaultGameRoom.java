package com.tfkfan.nettywebgame.game.room;

import com.tfkfan.nettywebgame.event.KeyDownPlayerEvent;
import com.tfkfan.nettywebgame.game.map.GameMap;
import com.tfkfan.nettywebgame.game.model.DefaultPlayer;
import com.tfkfan.nettywebgame.game.model.Direction;
import com.tfkfan.nettywebgame.game.model.Vector;
import com.tfkfan.nettywebgame.networking.message.MessageType;
import com.tfkfan.nettywebgame.networking.message.impl.outcoming.OutcomingMessage;
import com.tfkfan.nettywebgame.networking.message.impl.outcoming.OutcomingPlayerMessage;
import com.tfkfan.nettywebgame.networking.pack.init.GameRoomStartPack;
import com.tfkfan.nettywebgame.networking.pack.init.GameSettingsPack;
import com.tfkfan.nettywebgame.networking.pack.update.GameUpdatePack;
import com.tfkfan.nettywebgame.networking.pack.update.PlayerUpdatePack;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import com.tfkfan.nettywebgame.service.GameRoomManagementService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.tfkfan.nettywebgame.config.ApplicationProperties.RoomProperties;

@Slf4j
public class DefaultGameRoom extends AbstractGameRoom {
    private final GameMap gameMap;
    private final AtomicBoolean started = new AtomicBoolean(false);
    private final RoomProperties roomProperties;

    public DefaultGameRoom(GameMap gameMap, UUID gameRoomId,
                           GameRoomManagementService gameRoomManagementService, ScheduledExecutorService schedulerService,
                           RoomProperties roomProperties) {
        super(gameRoomId, gameRoomManagementService, schedulerService);
        this.gameMap = gameMap;
        this.roomProperties = roomProperties;
    }

    @Override
    public void onRoomCreated(List<PlayerSession> playerSessions) {
        if (playerSessions != null) {
            playerSessions.forEach(session -> {
                DefaultPlayer defaultPlayer = (DefaultPlayer) session.getPlayer();
                defaultPlayer.setPosition(new Vector(100.0, 100.0));
                gameMap.addPlayer(defaultPlayer);
            });
            super.onRoomCreated(playerSessions);
        }

        sendBroadcast(new OutcomingMessage(MessageType.JOIN_SUCCESS,
                new GameSettingsPack(roomProperties.getLoopRate())));
        log.debug("Room {} has been created", key());
    }

    @Override
    public void onRoomStarted() {
        this.started.set(false);
        sendBroadcast(new OutcomingMessage(MessageType.ROOM_START, new GameRoomStartPack()));
        log.debug("Room {} has been started", key());
    }

    @Override
    public void onBattleStarted() {
        this.started.set(true);
        sendBroadcast(new OutcomingPlayerMessage(MessageType.BATTLE_START));
        log.debug("Room {}. Battle has been started", key());
    }

    @Override
    public void onBattleEnded() {
        sendBroadcast(new OutcomingMessage(MessageType.ROOM_CLOSE));
        log.debug("Room {} has been ended", key());
    }

    //room's game loop
    @Override
    public void update() {
        if (!started.get()) return;
        final List<PlayerUpdatePack> playerUpdatePackList =
                gameMap.getPlayers()
                        .stream()
                        .peek(DefaultPlayer::update)
                        .map(DefaultPlayer::getUpdatePack)
                        .collect(Collectors.toList());

        for (DefaultPlayer currentPlayer : gameMap.getPlayers()) {
            final PlayerSession session = currentPlayer.getSession();
            send(session, new OutcomingPlayerMessage(session, MessageType.UPDATE,
                    new GameUpdatePack(
                            currentPlayer.getPrivateUpdatePack(),
                            playerUpdatePackList)));
        }
    }

    public void onPlayerKeyDown(KeyDownPlayerEvent event) {
        if (!started.get()) return;
        DefaultPlayer player = (DefaultPlayer) event.getSession().getPlayer();
        if (!player.getIsAlive()) return;
        Direction direction = Direction.valueOf(event.getInputId());
        player.updateState(direction, event.getState());
    }

    @Override
    public void onDestroy(List<PlayerSession> playerSessions) {
        onDestroy(playerSessions, playerSession -> gameMap.removePlayer((DefaultPlayer) playerSession.getPlayer()));
    }
}
