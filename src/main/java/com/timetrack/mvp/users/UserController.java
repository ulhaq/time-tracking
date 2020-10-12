package com.timetrack.mvp.users;

import java.util.List;
import java.util.Set;

import com.timetrack.mvp.roles.Role;
import com.timetrack.mvp.roles.RoleController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService service;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();

        for (User user : users) {
            user.add(linkTo(methodOn(this.getClass()).getUser(user.getId())).withSelfRel());
            user.add(linkTo(methodOn(this.getClass()).getUserRoles(user.getId())).withRel("roles"));
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = service.getUser(id);

        user.add(linkTo(methodOn(this.getClass()).getUser(id)).withSelfRel());
        user.add(linkTo(methodOn(this.getClass()).getUserRoles(id)).withRel("roles"));

        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/{id}/roles")
    public ResponseEntity<Set<Role>> getUserRoles(@PathVariable Long id) {
        Set<Role> roles = service.getUserRoles(id);

        for (Role role : roles) {
            role.add(linkTo(methodOn(RoleController.class).getRole(role.getId())).withSelfRel());
        }

        return ResponseEntity.ok(roles);
    }
}
