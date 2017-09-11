package com.mentoring.web;

import com.google.common.collect.Lists;
import com.mentoring.domain.entity.User;
import com.mentoring.service.RoleService;
import com.mentoring.service.UserService;
import com.mentoring.web.converter.Converter;
import com.mentoring.web.dto.user.GenericUserDto;
import com.mentoring.web.dto.user.RegistrationDto;
import com.mentoring.web.dto.user.UserDto;
import com.mentoring.web.exception.DuplicateEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * @author ivanovaolyaa
 * @version 7/13/2017
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    @Qualifier("userJpaService")
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private Converter<User, GenericUserDto> userConverter;

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "User created.")
    public void register(@RequestBody @Valid RegistrationDto registrationDto) throws DuplicateEmailException {
        if (!isPasswordConfirmed(registrationDto)) {
            throw new IllegalArgumentException("Password is not confirmed");
        }
        if (userService.isEmailExist(registrationDto.getEmail())) {
            throw new DuplicateEmailException("Email is already exists");
        }
        final User user = userConverter.convertToEntity(registrationDto);
        user.addRole(roleService.findByRoleName(registrationDto.getRole()));
        userService.save(user);
    }

    @PostMapping("/update")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseStatus(value = HttpStatus.CREATED, reason = "User profile updated.")
    public void updateProfile(@RequestBody @Valid UserDto userDto, HttpServletRequest request) {
        User user = userService.findUserByEmail(request.getUserPrincipal().getName());
        if (Objects.nonNull(user)) {
            User updatedUser = userConverter.update(user, userDto);
            userService.save(updatedUser);
        } else {
            throw new IllegalArgumentException("User is not exists");
        }
    }

    @GetMapping()
    @Secured("ROLE_ADMIN")
    public List<UserDto> getAllUsers() {
        List<UserDto> list = Lists.newArrayList();
        List<User> users = userService.findAll();

        for (User u: users) {
            list.add((UserDto) userConverter.convertToDto(u));
        }

        return list;
    }

    private boolean isPasswordConfirmed(final RegistrationDto dto) {
        return StringUtils.equals(dto.getPassword(), dto.getConfirmPassword());
    }

}
