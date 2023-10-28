package com.tfkfan.nettywebgame.event;

import com.google.gson.annotations.SerializedName;
import com.tfkfan.nettywebgame.service.AuthService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthEvent extends AbstractEvent{
    @SerializedName(AuthService.TOKEN_FIELD)
    private String bearerToken;

    public AuthEvent(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
