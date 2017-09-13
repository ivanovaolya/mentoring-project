package com.mentoring.service.impl.jpa;

import java.util.List;
import java.util.Objects;

import com.mentoring.domain.entity.User;
import com.mentoring.domain.repository.jpa.UserJpaDao;
import com.mentoring.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author ivanovaolyaa
 * @version 9/11/2017
 */
@Service
@Qualifier("userJpaService")
public class UserJpaServiceImpl implements UserService {

    @Autowired
    private UserJpaDao userJpaDao;

    @Override
    public void save(final User user) {
        if (Objects.isNull(userJpaDao.findByEmail(user.getEmail()))) {
            userJpaDao.create(user);
        } else {
            userJpaDao.update(user);
        }
    }

    @Override
    public void delete(final User user) {
        userJpaDao.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userJpaDao.findAll();
    }

    @Override
    public User findUserByPk(final Long pk) {
        return userJpaDao.find(pk);
    }

    @Override
    public User findUserByEmail(final String email) {
        return userJpaDao.findByEmail(email);
    }

    @Override
    public boolean isEmailExist(final String email) {
        return Objects.nonNull(findUserByEmail(email));
    }

}
