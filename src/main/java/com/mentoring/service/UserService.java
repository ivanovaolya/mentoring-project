package com.mentoring.service;

import com.mentoring.domain.entity.User;

import java.util.List;

/**
 * @author ivanovaolyaa
 * @version 7/13/2017
 */
public interface UserService {

    void save(User user);

    void delete(User user);

    List<User> findAll();

    User findUserByPk(Long pk);

    User findUserByEmail(String email);

    boolean isEmailExist(String email);

}
