package com.mentoring.web;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mentoring.config.TestSecurityConfig;
import com.mentoring.domain.entity.User;
import com.mentoring.service.RoleService;
import com.mentoring.service.UserService;
import com.mentoring.web.converter.Converter;
import com.mentoring.web.dto.user.GenericUserDto;
import com.mentoring.web.dto.user.RegistrationDto;
import com.mentoring.web.dto.user.UserDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ivanovaolyaa
 * @version 8/2/2017
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
@Import(TestSecurityConfig.class)
public class UserControllerTest {

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";

    private static final String USER_EMAIL = "olya.ivanova@gmail.com";
    private static final String USER_PASSWORD = "o123456";
    private static final String USER_FIRST_NAME = "Olya";
    private static final String USER_LAST_NAME = "Ivanova";
    private static final String USER_CONFIRMED_PASSWORD = "o123456";
    private static final String USER_UNCONFIRMED_PASSWORD = "11111111";

    private static final String URL_USERS = "/users";
    private static final String URL_USERS_REGISTER = Joiner.on("").join(URL_USERS, "/register");
    private static final String URL_USERS_UPDATE = Joiner.on("").join(URL_USERS, "/update");

    @MockBean
    private Converter<User, GenericUserDto> userConverter;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;


    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webAppContext)
                .apply(springSecurity()) //will perform all of the initial setup to integrate Spring Security with Spring MVC Test
                .build();
    }

    @Test
    @WithAnonymousUser
    public void shouldRegisterUser() throws Exception {
        final RegistrationDto dto = givenRegistrationDto(ROLE_USER);
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
        final RegistrationDto dto = givenRegistrationDto(ROLE_USER);
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
        final RegistrationDto dto = givenRegistrationDto(ROLE_USER);
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
    @WithMockUser(authorities = ROLE_USER)
    public void shouldUpdateUserProfile() throws Exception {
        final UserDto userDto = givenUserDto();
        final User user = givenUser();
        final String userJson = mapper.writeValueAsString(userDto);

        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(userConverter.update(user, userDto)).thenReturn(user);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URL_USERS_UPDATE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson);

        mockMvc.perform(requestBuilder).andExpect(status().is(HttpStatus.CREATED.value()));
        verify(userService).save(user);
    }

    @Test
    @WithMockUser(authorities = ROLE_USER)
    public void shouldNotUpdateUserProfileIfNotExists() throws Exception {
        final UserDto userDto = givenUserDto();
        final String userJson = mapper.writeValueAsString(userDto);

        when(userService.findUserByEmail(anyString())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URL_USERS_UPDATE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson);

        mockMvc.perform(requestBuilder).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        verify(userService, never()).save(any());
    }

    @Test
    @WithMockUser(authorities = ROLE_ADMIN)
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

    private RegistrationDto givenRegistrationDto(final String roleName) {
        final RegistrationDto dto = new RegistrationDto();

        dto.setEmail(USER_EMAIL);
        dto.setPassword(USER_PASSWORD);
        dto.setConfirmPassword(USER_CONFIRMED_PASSWORD);
        dto.setRole(roleName);

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
