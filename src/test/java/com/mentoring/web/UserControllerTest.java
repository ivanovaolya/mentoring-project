package com.mentoring.web;

import java.util.List;
import java.util.StringJoiner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mentoring.domain.entity.User;
import com.mentoring.service.UserService;
import com.mentoring.web.converter.Converter;
import com.mentoring.web.dto.user.GenericUserDto;
import com.mentoring.web.dto.user.RegistrationDto;
import com.mentoring.web.dto.user.UserDto;

import org.apache.tomcat.util.buf.StringUtils;
import org.assertj.core.util.Strings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ivanovaolyaa
 * @version 8/2/2017
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

    private static final String USER_EMAIL = "olya.ivanova@gmail.com";
    private static final String USER_PASSWORD = "o123456";
    private static final String USER_FIRST_NAME = "Olya";
    private static final String USER_LAST_NAME = "Ivanova";
    private static final String USER_CONFIRMED_PASSWORD = "o123456";
    private static final String USER_UNCONFIRMED_PASSWORD = "11111111";

    private static final String URL_USERS = "/users";
    private static final String URL_USERS_REGISTER = Joiner.on("").join(URL_USERS, "/register");
    private static final String URL_USERS_UPDATE = Joiner.on("").join(URL_USERS, "/{email}/update");

    @MockBean
    private Converter<User, GenericUserDto> userConverter;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldRegisterUser() throws Exception {
        final RegistrationDto dto = givenRegistrationDto();
        final User user = givenUser();
        final String registrationJson = mapper.writeValueAsString(dto);

        when(userService.isEmailExist(anyString())).thenReturn(Boolean.FALSE);
        when(userConverter.convertToEntity(any(RegistrationDto.class))).thenReturn(user);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URL_USERS_REGISTER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(registrationJson);

        mockMvc.perform(requestBuilder).andExpect(status().is(HttpStatus.CREATED.value()));
        verify(userService).save(user);
    }

    @Test
    public void shouldNotRegisterIfPasswordIsNotConfirmed() throws Exception {
        final RegistrationDto dto = givenRegistrationDto();
        dto.setConfirmPassword(USER_UNCONFIRMED_PASSWORD);
        final String registrationJson = mapper.writeValueAsString(dto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URL_USERS_REGISTER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(registrationJson);

        mockMvc.perform(requestBuilder).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        verify(userService, never()).save(any(User.class));
    }

    @Test
    public void shouldNotRegisterIfEmailExists() throws Exception {
        final RegistrationDto dto = givenRegistrationDto();
        final String registrationJson = mapper.writeValueAsString(dto);

        when(userService.isEmailExist(anyString())).thenReturn(Boolean.TRUE);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URL_USERS_REGISTER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(registrationJson);

        mockMvc.perform(requestBuilder).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        verify(userService, never()).save(any(User.class));
    }

    @Test
    public void shouldUpdateUserProfile() throws Exception {
        final UserDto userDto = givenUserDto();
        final User user = givenUser();
        final String userJson = mapper.writeValueAsString(userDto);

        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(user);
        when(userConverter.update(user, userDto)).thenReturn(user);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URL_USERS_UPDATE, USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson);

        mockMvc.perform(requestBuilder).andExpect(status().is(HttpStatus.CREATED.value()));
        verify(userService).save(user);
    }

    @Test
    public void shouldNotUpdateUserProfileIfNotExists() throws Exception {
        final UserDto userDto = givenUserDto();
        final String userJson = mapper.writeValueAsString(userDto);

        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URL_USERS_UPDATE, USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson);

        mockMvc.perform(requestBuilder).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        verify(userService, never()).save(any());
    }

    @Test
    public void shouldGetAllUsers() throws Exception {
        final User user = givenUser();
        final UserDto userDto = givenUserDto();
        final List<User> givenUsers = Lists.newArrayList(user);

        when(userService.findAll()).thenReturn(givenUsers);
        when(userConverter.convertToDto(user)).thenReturn(userDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URL_USERS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(USER_FIRST_NAME)))
                .andExpect(jsonPath("$[0].lastName", is(USER_LAST_NAME)))
                .andExpect(jsonPath("$[0].email", is(USER_EMAIL)));
    }

    private RegistrationDto givenRegistrationDto() {
        final RegistrationDto dto = new RegistrationDto();

        dto.setEmail(USER_EMAIL);
        dto.setPassword(USER_PASSWORD);
        dto.setConfirmPassword(USER_CONFIRMED_PASSWORD);

        return dto;
    }

    private UserDto givenUserDto() {
        final UserDto dto = new UserDto();

        dto.setFirstName(USER_FIRST_NAME);
        dto.setLastName(USER_LAST_NAME);
        dto.setEmail(USER_EMAIL);

        return dto;
    }

    private User givenUser() {
        final User user = mock(User.class);

        when(user.getEmail()).thenReturn(USER_EMAIL);
        when(user.getPassword()).thenReturn(USER_PASSWORD);
        when(user.getEmail()).thenReturn(USER_EMAIL);

        return user;
    }
}
