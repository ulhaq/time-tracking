package com.timetrack.mvp.payloads;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
     
@Getter
@Setter
public class LoginRequest {
    @NotEmpty(message = "{username.notEmpty}")
    String username;
    @NotEmpty(message = "{password.notEmpty}")
    String password;
}