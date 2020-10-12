package com.timetrack.mvp.roles;

public enum ERole {
    ADMIN, MANAGER, DRIVER;
    
    @Override
    public String toString(){
        return name();
    }
}
