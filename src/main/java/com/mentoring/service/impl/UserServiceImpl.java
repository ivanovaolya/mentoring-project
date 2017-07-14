package com.mentoring.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.mentoring.domain.entity.User;
import com.mentoring.domain.repository.UserRepository;
import com.mentoring.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ivanovaolyaa
 * @version 7/13/2017
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void save(final User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(final User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void remove(final User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return Lists.newArrayList(userRepository.findAll());
    }

    @Override
    public User findByPk(final Long pk) {
        return userRepository.findOne(pk);
    }

    @Override
    public boolean isEmailExist(final String email) {
        final User user = userRepository.findByEmail(email);
        return (user != null);
    }
}
