package com.tfkfan.nettywebgame.game.model;

import com.tfkfan.nettywebgame.game.room.DefaultGameRoom;
import com.tfkfan.nettywebgame.networking.session.PlayerSession;
import com.tfkfan.nettywebgame.pack.init.PlayerInitPack;
import com.tfkfan.nettywebgame.pack.privat.PrivatePlayerUpdatePack;
import com.tfkfan.nettywebgame.pack.update.PlayerUpdatePack;
import com.tfkfan.nettywebgame.shared.Constants;
import com.tfkfan.nettywebgame.shared.Direction;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class DefaultPlayer extends AbstractPlayer<DefaultGameRoom, PlayerInitPack, PlayerUpdatePack, PrivatePlayerUpdatePack> {
    public DefaultPlayer(Long id,  DefaultGameRoom gameRoom, PlayerSession session) {
        super(id, gameRoom, session);
    }


    public void updateState(Direction direction, Boolean state) {
        movingState.put(direction, state);

        this.direction = direction;

        isMoving = true;
        if (this.movingState.get(Direction.UP) && this.movingState.get(Direction.RIGHT))
            this.direction = Direction.UPRIGHT;
        else if (this.movingState.get(Direction.UP) && this.movingState.get(Direction.LEFT))
            this.direction = Direction.UPLEFT;
        else if (this.movingState.get(Direction.DOWN) && this.movingState.get(Direction.RIGHT))
            this.direction = Direction.DOWNRIGHT;
        else if (this.movingState.get(Direction.DOWN) && this.movingState.get(Direction.LEFT))
            this.direction = Direction.DOWNLEFT;
        else if (this.movingState.get(Direction.UP))
            this.direction = Direction.UP;
        else if (this.movingState.get(Direction.DOWN))
            this.direction = Direction.DOWN;
        else if (this.movingState.get(Direction.RIGHT))
            this.direction = Direction.RIGHT;
        else if (this.movingState.get(Direction.LEFT))
            this.direction = Direction.LEFT;
        else
            this.isMoving = false;
    }

    @Override
    public void update() {
        double ABS_PLAYER_SPEED = Constants.ABS_PLAYER_SPEED;

        if (direction == Direction.RIGHT && isMoving)
            velocity.setX(ABS_PLAYER_SPEED);
        else if (direction == Direction.LEFT && isMoving)
            velocity.setX(-ABS_PLAYER_SPEED);
        else
            velocity.setX(0.0);

        if (direction == Direction.UP && isMoving)
            velocity.setY(-ABS_PLAYER_SPEED);
        else if (direction == Direction.DOWN && isMoving)
            velocity.setY(ABS_PLAYER_SPEED);
        else
            velocity.setY(0.0);

        if (direction == Direction.DOWNRIGHT && isMoving) {
            velocity.setX(ABS_PLAYER_SPEED);
            velocity.setY(ABS_PLAYER_SPEED);
        } else if (direction == Direction.UPRIGHT && isMoving) {
            velocity.setX(ABS_PLAYER_SPEED);
            velocity.setY(-ABS_PLAYER_SPEED);
        } else if (direction == Direction.DOWNLEFT && isMoving) {
            velocity.setX(-ABS_PLAYER_SPEED);
            velocity.setY(ABS_PLAYER_SPEED);
        } else if (direction == Direction.UPLEFT && isMoving) {
            velocity.setX(-ABS_PLAYER_SPEED);
            velocity.setY(-ABS_PLAYER_SPEED);
        }

        position.sum(velocity);
    }

    @Override
    public PlayerUpdatePack getUpdatePack() {
        return new PlayerUpdatePack(id, position);
    }

    @Override
    public PlayerInitPack getInitPack() {
        return new PlayerInitPack(id, position);
    }

    @Override
    public PlayerInitPack init() {
        return getInitPack();
    }

    @Override
    public PrivatePlayerUpdatePack getPrivateUpdatePack() {
        return new PrivatePlayerUpdatePack(id);
    }
}
