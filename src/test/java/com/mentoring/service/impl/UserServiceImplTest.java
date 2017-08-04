package com.mentoring.service.impl;

import java.util.List;

import com.google.common.collect.Lists;
import com.mentoring.domain.entity.User;
import com.mentoring.domain.repository.UserRepository;
import com.mentoring.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author ivanovaolyaa
 * @version 8/2/2017
 */
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    private final String USER_EMAIL = "olya.ivanova@gmail.com";
    private final Long USER_PK = 1L;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Test
    public void shouldSaveUser() {
        final User givenUser = givenUser();
        userService.save(givenUser);

        verify(userRepository).save(givenUser);
    }

    @Test
    public void shouldDeleteUser() {
        final User givenUser = givenUser();
        userService.delete(givenUser);

        verify(userRepository).delete(givenUser);
    }

    @Test
    public void shouldFindAllUsers() {
        final List<User> givenUsers = Lists.newArrayList(mock(User.class), mock(User.class));
        when(userRepository.findAll()).thenReturn(givenUsers);

        final List<User> users = userService.findAll();

        verify(userRepository).findAll();
        assertEquals(givenUsers.size(), users.size());
    }
    
    @Test
    public void shouldFindUserByEmail() {
        final User givenUser = givenUser();

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(givenUser);
        final User user = userService.findUserByEmail(USER_EMAIL);

        verify(userRepository).findByEmail(USER_EMAIL);
        assertEquals(user.getEmail(), givenUser.getEmail());
    }

    @Test
    public void shouldFindUserByPk() {
        final User givenUser = givenUser();

        when(userRepository.findOne(USER_PK)).thenReturn(givenUser);
        final User user = userService.findUserByPk(USER_PK);

        verify(userRepository).findOne(USER_PK);
        assertEquals(user.getUserPk(), givenUser.getUserPk());
    }

    @Test
    public void shouldReturnTrueIfUserExists() {
        final User givenUser = givenUser();

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(givenUser);
        final boolean isEmailExist = userService.isEmailExist(USER_EMAIL);

        assertTrue(isEmailExist);
    }

    @Test
    public void shouldReturnFalseIfUserNotExists() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        final boolean isEmailExist = userService.isEmailExist(anyString());

        assertFalse(isEmailExist);
    }

    private User givenUser() {
        final User user = mock(User.class);

        when(user.getEmail()).thenReturn(USER_EMAIL);
        when(user.getUserPk()).thenReturn(USER_PK);

        return user;
    }
}
