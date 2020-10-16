package com.timetrack.mvp.users;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Set;

import com.timetrack.mvp.roles.Role;
import com.timetrack.mvp.roles.RoleController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = service.getAllUsers();

        for (UserDto user : users) {
            user.add(linkTo(methodOn(this.getClass()).getUser(user.getId())).withSelfRel().withType("GET"));
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto user = service.getUser(id);

        user.add(linkTo(methodOn(this.getClass()).getUser(id)).withSelfRel().withType("GET"));
        user.add(linkTo(methodOn(this.getClass()).getUserRoles(id)).withRel("roles").withType("GET"));

        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/{id}/roles")
    public ResponseEntity<Set<Role>> getUserRoles(@PathVariable Long id) {
        Set<Role> roles = service.getUserRoles(id);

        for (Role role : roles) {
            role.add(linkTo(methodOn(RoleController.class).getRole(role.getId())).withSelfRel().withType("GET"));
        }

        return ResponseEntity.ok(roles);
    }
}
