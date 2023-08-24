package com.tfkfan.nettywebgame.pack.privat;

import com.tfkfan.nettywebgame.pack.PrivateUpdatePack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivatePlayerUpdatePack implements PrivateUpdatePack {
    private Long id;
}
