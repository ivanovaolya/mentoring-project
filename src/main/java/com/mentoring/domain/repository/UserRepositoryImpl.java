package com.mentoring.domain.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mentoring.domain.entity.User;

import org.springframework.stereotype.Repository;


/**
 * @author ivanovaolyaa
 * @version 7/14/2017
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(final User user) {
        entityManager.persist(user);
    }

    @Override
    public User update(final User user) {
        return entityManager.merge(user);
    }

    @Override
    public void remove(final User user) {
        entityManager.remove(user);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u ", User.class).getResultList();
    }

    @Override
    public User findByPk(final Long pk) {
        final List<User> users = entityManager.createQuery("SELECT u FROM User u where u.pk = :pk", User.class)
                .setParameter("pk", pk).getResultList();

        return findUnique(users);
    }

    @Override
    public User findByEmail(final String email) {
        final List<User> users = entityManager.createQuery("SELECT u FROM User u where u.email = :email", User.class)
                .setParameter("email", email).getResultList();

        return findUnique(users);
    }

    private User findUnique(final List<User> users) {
        return (users.isEmpty() ? null : users.get(0));
    }

}
