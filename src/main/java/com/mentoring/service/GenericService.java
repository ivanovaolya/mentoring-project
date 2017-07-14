package com.mentoring.service;

import java.util.List;

/**
 * @author ivanovaolyaa
 * @version 7/14/2017
 */
public interface GenericService<T> {
    void save(T entity);

    T update(T entity);

    void remove(T entity);

    List<T> findAll();

    T findByPk(Long pk);
}
