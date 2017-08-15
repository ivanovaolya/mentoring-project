package com.mentoring.service;

import com.mentoring.domain.entity.Role;

/**
 * @author ivanovaolyaa
 * @version 8/14/2017
 */
public interface RoleService {

    Role findByRoleName(final String roleName);

}
