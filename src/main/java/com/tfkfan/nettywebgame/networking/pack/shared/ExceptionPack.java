package com.tfkfan.nettywebgame.networking.pack.shared;

import com.tfkfan.nettywebgame.networking.pack.SharedPack;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionPack implements SharedPack {
    private String message;
}
