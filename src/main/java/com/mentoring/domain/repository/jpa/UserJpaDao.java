package com.mentoring.domain.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.mentoring.domain.entity.User;

import org.springframework.stereotype.Repository;

/**
 * @author ivanovaolyaa
 * @version 9/11/2017
 */
@Repository
public class UserJpaDao implements GenericJpaDao<User> {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void create(final User user) {
        em.persist(user);
    }

    @Override
    @Transactional
    public <S extends User> S update(final S user) {
        return em.merge(user);
    }

    @Override
    public User find(final Long id) {
        return (User) em.createQuery("select u from User u where u.user_pk = :id").setParameter("id", id)
                .getSingleResult();
    }

    public User findByEmail(final String email) {
        User user;
        try {
            user = (User) em.createQuery("select u from User u where u.email = :email").setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException ex) {
            user = null;
        }

        return user;
    }

    @Override
    @Transactional
    public void delete(final User user) {
        em.remove(user);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u").getResultList();
    }
}
