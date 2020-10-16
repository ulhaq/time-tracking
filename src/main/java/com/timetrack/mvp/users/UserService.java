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

    public boolean isUsernameTaken(String username) {
        return repo.existsByUsername(username);
    }
    
    public boolean isEmailTaken(String email) {
        return repo.existsByEmail(email);
    }

    public List<UserDto> getAllUsers() {
        return repo.findAllProjectedBy();
    }

    public UserDto getUser(Long id) {
        return repo.findProjectedById(id).orElseThrow(() -> new NoRecordFoundException("User not found"));
    }

    public Set<Role> getUserRoles(Long id) {
        return repo.findById(id).orElseThrow(() -> new NoRecordFoundException("User not found")).getRoles();
    }
}
