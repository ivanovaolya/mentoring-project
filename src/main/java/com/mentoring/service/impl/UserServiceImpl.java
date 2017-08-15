package com.mentoring.service.impl;

import com.google.common.collect.Lists;
import com.mentoring.domain.entity.Role;
import com.mentoring.domain.entity.User;
import com.mentoring.domain.repository.RoleRepository;
import com.mentoring.domain.repository.UserRepository;
import com.mentoring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author ivanovaolyaa
 * @version 7/13/2017
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void save(final User user) {
        userRepository.save(user);
    }

    @Transactional
    public void delete(final User user) {
        userRepository.delete(user);
    }

    public List<User> findAll() {
        return Lists.newArrayList(userRepository.findAll());
    }

    public User findUserByPk(final Long pk) {
        return userRepository.findOne(pk);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean isEmailExist(final String email) {
        final User user = userRepository.findByEmail(email);
        return (user != null);
    }
}
