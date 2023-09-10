package com.tfkfan.nettywebgame.game.model;

import com.tfkfan.nettywebgame.networking.pack.Initializable;
import com.tfkfan.nettywebgame.game.room.GameRoom;
import com.tfkfan.nettywebgame.networking.pack.InitPack;
import com.tfkfan.nettywebgame.networking.pack.UpdatePack;
import com.tfkfan.nettywebgame.networking.pack.init.IInitPackGetter;
import com.tfkfan.nettywebgame.networking.pack.update.IUpdatePackGetter;
import com.tfkfan.nettywebgame.shared.Vector;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public abstract class GameEntity<ID,GR extends GameRoom,
        IP extends InitPack, UP extends UpdatePack> extends AbstractEntity<ID>  implements
        Initializable<IP>,
        IUpdatePackGetter<UP>,
        IInitPackGetter<IP> {

    protected Boolean isMoving;
    protected Boolean isAlive;
    protected Vector position;
    protected Vector velocity;
    protected Vector acceleration;
    protected GR gameRoom;

    public GameEntity(ID id, GR gameRoom) {
        super(id);
        isMoving = false;
        isAlive = true;
        position = new Vector(0.0, 0.0);
        velocity = new Vector(0.0,0.0);
        acceleration = new Vector(0.0,0.0);
        this.gameRoom = gameRoom;
    }

    @Override
    public IP init() {
        return getInitPack();
    }
}
