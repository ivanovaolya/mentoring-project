package com.mentoring.domain.repository.jpa;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mentoring.domain.entity.Phone;

import org.hibernate.FetchMode;
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
        return em.createQuery("select p from Phone p join fetch p.user u").getResultList();
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
