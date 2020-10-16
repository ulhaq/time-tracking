package com.timetrack.mvp.users;

import org.springframework.hateoas.RepresentationModel;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class UserDto extends RepresentationModel<UserDto> {
    private final Long id;
    private final String name;
    private final String username;
    private final String email;
}
