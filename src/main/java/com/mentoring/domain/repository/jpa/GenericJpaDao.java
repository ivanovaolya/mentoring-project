package com.mentoring.domain.repository.jpa;

import java.util.List;

/**
 * @author ivanovaolyaa
 * @version 9/11/2017
 */
public interface GenericJpaDao<T> {

    void create(T entity);

    <S extends T> S update(S entity);

    T find(Long id);

    void delete(T entity);

    List<T> findAll();
}
