package com.timetrack.mvp.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.timetrack.mvp.users.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    Logger log = LoggerFactory.getLogger(UniqueUsernameValidator.class);

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {//
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            return !userService.isUsernameTaken(value);
        } catch (Exception e) {
            log.error(String.format("UniqueUsername Validation failed : %s", e.getMessage()));
        }
        return false;
    }
}