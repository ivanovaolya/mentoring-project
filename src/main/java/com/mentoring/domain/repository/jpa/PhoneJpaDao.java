package com.mentoring.domain.repository.jpa;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mentoring.domain.entity.Phone;

import org.springframework.stereotype.Repository;

/**
 * @author ivanovaolyaa
 * @version 9/13/2017
 */
@Repository
public class PhoneJpaDao implements GenericJpaDao<Phone> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Phone> findAll() {
        EntityGraph<Phone> fetchUser = em.createEntityGraph(Phone.class);
        fetchUser.addSubgraph("user");

        return em.createQuery("select p from Phone p")
                .setHint("javax.persistence.fetchgraph", fetchUser)
                .getResultList();
    }

    @Override
    public void create(final Phone entity) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Phone> S update(final S entity) {
        throw new NotImplementedException();
    }

    @Override
    public Phone find(final Long id) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(final Phone entity) {
        throw new NotImplementedException();
    }

}
