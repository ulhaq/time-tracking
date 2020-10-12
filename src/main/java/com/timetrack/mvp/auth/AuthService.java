package com.timetrack.mvp.auth;

import java.util.Set;

import javax.validation.Valid;

import com.timetrack.mvp.exceptions.UserAlreadyExists;
import com.timetrack.mvp.payloads.LoginRequest;
import com.timetrack.mvp.payloads.RegisterRequest;
import com.timetrack.mvp.roles.ERole;
import com.timetrack.mvp.roles.Role;
import com.timetrack.mvp.roles.RoleRepository;
import com.timetrack.mvp.users.User;
import com.timetrack.mvp.users.UserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserRepository repo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtUtils.generateJwtToken(authentication);
    }

    public User register(@Valid RegisterRequest user) {
        if (repo.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExists("This username is already taken");
        }

        if (repo.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExists("This email is already taken");
        }

        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        encodePassword(newUser, user);

        Set<ERole> userRoles = user.getRoles();
        if (userRoles != null) {
            Set<Role> roles = roleRepo.findByNameIn(userRoles);
            newUser.setRoles(roles);
        }

        return repo.save(newUser);
    }

    private void encodePassword(User newUser, RegisterRequest user) {
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
