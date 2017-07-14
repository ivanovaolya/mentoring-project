package com.mentoring.domain.repository;

import java.util.List;
/**
 * @author ivanovaolyaa
 * @version 7/14/2017
 */
public interface GenericCrudRepository<T> {

    void save(T entity);

    T update(T entity);

    void remove(T entity);

    List<T> findAll();

    T findByPk(Long pk);

}
