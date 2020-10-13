package com.timetrack.mvp.roles;

import java.util.List;

import com.timetrack.mvp.users.UserController;
import com.timetrack.mvp.users.User;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    private RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping("roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = service.getAllRoles();

        for (Role role : roles) {
            role.add(linkTo(methodOn(this.getClass()).getRole(role.getId())).withSelfRel().withType("GET"));
            role.add(linkTo(methodOn(this.getClass()).getRoleUsers(role.getId())).withRel("users").withType("GET"));
        }

        return ResponseEntity.ok(roles);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        Role role = service.getRole(id);

        role.add(linkTo(methodOn(this.getClass()).getRole(id)).withSelfRel().withType("GET"));
        role.add(linkTo(methodOn(this.getClass()).getRoleUsers(id)).withRel("users").withType("GET"));

        return ResponseEntity.ok(role);
    }

    @GetMapping("/roles/{id}/users")
    public ResponseEntity<List<User>> getRoleUsers(@PathVariable Long id) {
        List<User> users = service.getRoleUsers(id);

        for (User user : users) {
            user.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel().withType("GET"));
        }

        return ResponseEntity.ok(users);
    }
}
