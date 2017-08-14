package com.mentoring.web.config.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.Sets;
import com.mentoring.domain.entity.User;
import com.mentoring.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author ivanovaolyaa
 * @version 8/10/2017
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        final User user = userService.findUserByEmail(email);

        if (Objects.isNull(user)) {
            throw new BadCredentialsException("No user with email '" + email + "'");
        }

        if (!user.getPassword().equals(password)) {
            throw new BadCredentialsException("Bad credentials");
        }

        final UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), user.getRoles());
        final Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        return new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return true;
    }

}
