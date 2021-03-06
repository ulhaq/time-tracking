package com.timetrack.mvp.payloads;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.timetrack.mvp.roles.ERole;
import com.timetrack.mvp.validations.UniqueEmail;
import com.timetrack.mvp.validations.UniqueUsername;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotEmpty(message = "{name.notEmpty}")
    @Size(message = "{name.size}", min = 2, max = 25)
    private String name;
    @NotEmpty(message = "{username.notEmpty}")
    @UniqueUsername(message = "{username.taken}")
    @Size(message = "{username.size}", min = 2, max = 25)
    private String username;
    @NotEmpty(message = "{email.notEmpty}")
    @UniqueEmail(message = "{email.taken}")
    @Email(message = "{email.valid}")
    private String email;
    @NotEmpty(message = "{password.notEmpty}")
    @Size(message = "{password.size}", min = 6, max = 50)
    private String password;
    Set<ERole> roles;
}
