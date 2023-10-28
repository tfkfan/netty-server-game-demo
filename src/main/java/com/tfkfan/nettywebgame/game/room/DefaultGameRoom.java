package com.tfkfan.nettywebgame.game.room;

import com.tfkfan.nettywebgame.config.RoomProperties;
import com.tfkfan.nettywebgame.event.InitPlayerEvent;
import com.tfkfan.nettywebgame.event.KeyDownPlayerEvent;
import com.tfkfan.nettywebgame.event.MouseDownPlayerEvent;
import com.tfkfan.nettywebgame.event.MouseMovePlayerEvent;
import com.tfkfan.nettywebgame.game.map.GameMap;
import com.tfkfan.nettywebgame.game.model.DefaultPlayer;
import com.tfkfan.nettywebgame.networking.message.Message;
import com.tfkfan.nettywebgame.networking.message.impl.outcoming.OutcomingMessage;
import com.tfkfan.nettywebgame.networking.message.impl.outcoming.OutcomingPlayerMessage;
import com.tfkfan.nettywebgame.networking.pack.init.GameInitPack;
import com.tfkfan.nettywebgame.networking.pack.privat.PrivatePlayerUpdatePack;
import com.tfkfan.nettywebgame.networking.pack.shared.GameRoomStartPack;
import com.tfkfan.nettywebgame.networking.pack.shared.GameSettingsPack;
import com.tfkfan.nettywebgame.networking.pack.update.GameUpdatePack;
import com.tfkfan.nettywebgame.networking.pack.update.PlayerUpdatePack;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import com.tfkfan.nettywebgame.service.GameRoomService;
import com.tfkfan.nettywebgame.shared.Direction;
import com.tfkfan.nettywebgame.shared.Vector;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
public class DefaultGameRoom extends AbstractGameRoom {
    private final GameMap gameMap;

    private final AtomicBoolean started = new AtomicBoolean(false);

    private final ScheduledExecutorService schedulerService;
    private final RoomProperties roomProperties;

    public DefaultGameRoom(GameMap gameMap, UUID gameRoomId,
                           GameRoomService gameRoomService, ScheduledExecutorService schedulerService,
                           RoomProperties roomProperties) {
        super(gameRoomId, gameRoomService);
        this.gameMap = gameMap;
        this.schedulerService = schedulerService;
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

        sendBroadcast(ps -> new OutcomingMessage(Message.CONNECT_SUCCESS,
                new GameSettingsPack(
                        roomProperties.getLooprate())));
        log.info("Room {} has been created", key());
    }

    @Override
    public void onRoomStarted() {
        this.started.set(false);
        sendBroadcast(new OutcomingMessage(Message.ROOM_START,
                new GameRoomStartPack(OffsetDateTime.now().plus(roomProperties.getStartdelay(), ChronoUnit.MILLIS).toInstant().toEpochMilli())));
        schedulerService.schedule(this::onBattleStarted, roomProperties.getStartdelay(), TimeUnit.MILLISECONDS);
        log.info("Room {} has been started", key());
    }

    @Override
    public void onBattleStarted() {
        log.info("Room {}. Battle has been started", key());
        this.started.set(true);
        sendBroadcast((s) -> new OutcomingPlayerMessage(Message.BATTLE_START));
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
            final PrivatePlayerUpdatePack updatePack = currentPlayer.getPrivateUpdatePack();
            final PlayerSession session = currentPlayer.getSession();
            send(session, new OutcomingPlayerMessage(session, Message.UPDATE,
                    new GameUpdatePack(
                            updatePack,
                            playerUpdatePackList)));
        }
    }

    public void onPlayerMouseClick(MouseDownPlayerEvent event) {
    }

    public void onPlayerMouseMove(MouseMovePlayerEvent event) {
    }

    public void onPlayerKeyDown(KeyDownPlayerEvent event) {
        if (!started.get()) return;
        DefaultPlayer player = (DefaultPlayer) event.getSession().getPlayer();
        if (!player.getIsAlive()) return;
        Direction direction = Direction.valueOf(event.getInputId());
        player.updateState(direction, event.getState());
    }

    public void onPlayerInitRequest(InitPlayerEvent initEvent) {
        PlayerSession session = initEvent.getSession();
        send(session, new OutcomingPlayerMessage(session, Message.INIT,
                new GameInitPack(((DefaultPlayer) session.getPlayer()).getInitPack(),
                        roomProperties.getLooprate(),
                        gameMap.alivePlayers())));
    }

    @Override
    public void onDestroy(List<PlayerSession> playerSessions) {
        playerSessions.forEach(playerSession -> gameMap.removePlayer((DefaultPlayer) playerSession.getPlayer()));
        super.onDestroy(playerSessions);
    }
}
