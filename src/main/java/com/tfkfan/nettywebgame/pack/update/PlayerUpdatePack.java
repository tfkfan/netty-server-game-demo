package com.tfkfan.nettywebgame.pack.update;

import com.tfkfan.nettywebgame.pack.UpdatePack;
import com.tfkfan.nettywebgame.shared.Vector;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerUpdatePack implements UpdatePack {
    private Long id;
    private Vector position;
}
