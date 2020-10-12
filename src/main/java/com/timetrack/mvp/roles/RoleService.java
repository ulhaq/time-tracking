package com.timetrack.mvp.roles;

import java.util.List;

import com.timetrack.mvp.exceptions.NoRecordFoundException;
import com.timetrack.mvp.users.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository repo;

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Role> getAllRoles() {
        return repo.findAll();
    }

    public Role getRole(Long id) {
        return repo.findById(id).orElseThrow(() -> new NoRecordFoundException("Role not found"));
    }

    public List<User> getRoleUsers(Long id) {
        return this.getRole(id).getUsers();
    }
}
