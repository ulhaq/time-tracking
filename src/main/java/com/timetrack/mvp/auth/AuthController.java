package com.timetrack.mvp.auth;

import javax.validation.Valid;

import com.timetrack.mvp.exceptions.UserAlreadyExists;
import com.timetrack.mvp.payloads.JwtResponse;
import com.timetrack.mvp.payloads.LoginRequest;
import com.timetrack.mvp.payloads.RegisterRequest;
import com.timetrack.mvp.users.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    private AuthService authservise;
    
    public AuthController(AuthService authservise) {
        this.authservise = authservise;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        String jwt = authservise.login(loginRequest);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest registerRequest) throws UserAlreadyExists {
        User user = authservise.register(registerRequest);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/auth/token/refresh")
    public String tokenRefresh() {
        return "refresh token";
    }
}
