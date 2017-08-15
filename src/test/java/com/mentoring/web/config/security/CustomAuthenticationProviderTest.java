package com.mentoring.web.config.security;

import com.mentoring.domain.entity.User;
import com.mentoring.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author ivanovaolyaa
 * @version 8/15/2017
 */
@RunWith(SpringRunner.class)
public class CustomAuthenticationProviderTest {

    private static final String USER_EMAIL = "olya.ivanova@gmail.com";
    private static final String USER_PASSWORD = "o123456";

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();

    @Test(expected = BadCredentialsException.class)
    public void shouldNotAuthenticateIfUserNotFound() {
        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(null);
        authProvider.authenticate(givenAuthentication());
    }

    @Test(expected = BadCredentialsException.class)
    public void shouldNotAuthenticateIfBadCredentials() {
        final User user = mock(User.class);

        when(user.getPassword()).thenReturn(USER_PASSWORD + "!");
        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(user);

        authProvider.authenticate(givenAuthentication());
    }

    @Test
    public void shouldAuthenticateUser() {
        final User user = mock(User.class);
        final Authentication auth = givenAuthentication();
        when(user.getPassword()).thenReturn(USER_PASSWORD);
        when(user.getEmail()).thenReturn(USER_EMAIL);
        when(userService.findUserByEmail(auth.getName())).thenReturn(user);

        final Authentication authentication = authProvider.authenticate(auth);

        assertNotNull(authentication);
    }

    private Authentication givenAuthentication() {
        final Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn(USER_EMAIL);
        when(authentication.getCredentials()).thenReturn(USER_PASSWORD);

        return authentication;
    }

}
