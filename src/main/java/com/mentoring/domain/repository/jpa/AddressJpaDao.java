package com.mentoring.domain.repository.jpa;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mentoring.domain.entity.Address;

import org.springframework.stereotype.Repository;

/**
 * @author ivanovaolyaa
 * @version 9/13/2017
 */
@Repository
public class AddressJpaDao implements GenericJpaDao<Address> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Address> findAll() {
        return em.createQuery("select a from Address a join fetch a.user u").getResultList();
    }

    @Override
    public void create(final Address entity) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends Address> S update(final S entity) {
        throw new NotImplementedException();
    }

    @Override
    public Address find(final Long id) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(final Address entity) {
        throw new NotImplementedException();
    }

}
