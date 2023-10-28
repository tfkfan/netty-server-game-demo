package com.tfkfan.nettywebgame.networking.pack.shared;

import com.tfkfan.nettywebgame.networking.pack.Pack;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionPack implements Pack {
    private String message;
}
