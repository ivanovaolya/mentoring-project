package com.mentoring.service;

import com.mentoring.domain.entity.User;

/**
 * @author ivanovaolyaa
 * @version 7/13/2017
 */
public interface UserService extends GenericService<User> {

    boolean isEmailExist(String email);

}
