package com.timetrack.mvp.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.timetrack.mvp.users.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    Logger log = LoggerFactory.getLogger(UniqueEmailValidator.class);

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {//
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println("#######################"+value);
        try {
            return !userService.isEmailTaken(value);
        } catch (Exception e) {
            log.error(String.format("UniqueEmail Validation failed : %s", e.getMessage()));
        }

        return false;
    }
}