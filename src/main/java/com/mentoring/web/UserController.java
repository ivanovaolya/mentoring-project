package com.mentoring.web;

import java.util.List;

import javax.validation.Valid;

import com.mentoring.domain.entity.User;
import com.mentoring.web.dto.RegistrationDto;
import com.mentoring.service.UserService;
import com.mentoring.web.exception.DuplicateEmailException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

/**
 * @author ivanovaolyaa
 * @version 7/13/2017
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "User created.")
    public void register(@RequestBody @Valid RegistrationDto registrationDto) throws DuplicateEmailException {
        if (!isPasswordConfirmed(registrationDto)) {
            throw new IllegalArgumentException("Password is not confirmed");
        }
        if (userService.isEmailExist(registrationDto.getEmail())) {
            throw new DuplicateEmailException("Email is already exists");
        }
        userService.save(convertToEntity(registrationDto));
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    private boolean isPasswordConfirmed(final RegistrationDto dto) {
        return StringUtils.equals(dto.getPassword(), dto.getConfirmPassword());
    }

    private User convertToEntity(final RegistrationDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }
}