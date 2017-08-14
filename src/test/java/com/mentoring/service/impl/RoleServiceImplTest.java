package com.mentoring.service.impl;

import com.mentoring.domain.entity.Role;
import com.mentoring.domain.repository.RoleRepository;
import com.mentoring.service.RoleService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author ivanovaolyaa
 * @version 8/14/2017
 */
@RunWith(SpringRunner.class)
public class RoleServiceImplTest {

    private final Long ROLE_PK = 1L;
    private final String ROLE_NAME = Role.USER;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService = new RoleServiceImpl();

    @Test
    public void shouldFindByRoleName() {
        final Role role = givenRole();

        when(roleRepository.findByRoleName(ROLE_NAME)).thenReturn(role);

        final Role actualRole = roleService.findByRoleName(ROLE_NAME);

        verify(roleRepository).findByRoleName(anyString());
        assertEquals(ROLE_PK, actualRole.getRolePk());
        assertEquals(ROLE_NAME, actualRole.getRoleName());
        assertEquals(ROLE_NAME, actualRole.getAuthority());
    }

    private Role givenRole() {
        final Role role = mock(Role.class);

        when(role.getRolePk()).thenReturn(ROLE_PK);
        when(role.getRoleName()).thenReturn(ROLE_NAME);
        when(role.getAuthority()).thenReturn(ROLE_NAME);

        return role;
    }
}
