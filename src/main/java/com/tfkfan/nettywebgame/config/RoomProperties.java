package com.tfkfan.nettywebgame.config;

import com.tfkfan.nettywebgame.shared.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomProperties {
    private Long looprate = Constants.DEFAULT_LOOPRATE;
    private Long initdelay = Constants.ROOM_INIT_DELAY;
    private Long startdelay = Constants.ROOM_START_DELAY;
    private Long maxplayers = Constants.MAX_PLAYERS;
}
