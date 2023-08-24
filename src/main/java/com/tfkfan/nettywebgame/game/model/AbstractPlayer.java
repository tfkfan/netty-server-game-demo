package com.tfkfan.nettywebgame.game.model;

import com.tfkfan.nettywebgame.game.room.GameRoom;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import com.tfkfan.nettywebgame.pack.InitPack;
import com.tfkfan.nettywebgame.pack.PrivateUpdatePack;
import com.tfkfan.nettywebgame.pack.Updatable;
import com.tfkfan.nettywebgame.pack.UpdatePack;
import com.tfkfan.nettywebgame.pack.update.IPrivateUpdatePackGetter;
import com.tfkfan.nettywebgame.shared.Direction;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Accessors(chain = true)
@Getter
@Setter
public abstract class AbstractPlayer< GR extends GameRoom,
        IP extends InitPack, UP extends UpdatePack, PUP extends PrivateUpdatePack>
        extends GameEntity<Long, GR, IP, UP> implements Player, Updatable, IPrivateUpdatePackGetter<PUP> {
    protected PlayerSession session;
    protected Direction direction;
    protected Map<Direction, Boolean> movingState;

    public AbstractPlayer(Long id, GR gameRoom, PlayerSession session) {
        super(id, gameRoom);
        this.session = session;
        movingState = Arrays.stream(Direction.values()).collect(Collectors.toMap(direction -> direction, direction -> false));
        direction = Direction.UP;
    }

    @Override
    public IP init() {
        return getInitPack();
    }
}
