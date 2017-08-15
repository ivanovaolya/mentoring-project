package com.mentoring.service.impl;

import com.mentoring.domain.entity.Role;
import com.mentoring.domain.repository.RoleRepository;
import com.mentoring.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ivanovaolyaa
 * @version 8/14/2017
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByRoleName(final String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

}
