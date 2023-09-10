package com.tfkfan.nettywebgame.networking.pack.privat;

import com.tfkfan.nettywebgame.networking.pack.PrivateUpdatePack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivatePlayerUpdatePack implements PrivateUpdatePack {
    private Long id;
}