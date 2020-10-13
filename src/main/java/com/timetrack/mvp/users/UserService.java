package com.timetrack.mvp.users;

import java.util.List;
import java.util.Set;

import com.timetrack.mvp.exceptions.NoRecordFoundException;
import com.timetrack.mvp.roles.Role;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User getUser(Long id) {
        return repo.findById(id).orElseThrow(() -> new NoRecordFoundException("User not found"));
    }

    public Set<Role> getUserRoles(Long id) {
        return this.getUser(id).getRoles();
    }
}
