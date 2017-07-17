package com.mentoring.web.converter;

/**
 * @author ivanovaolyaa
 * @version 7/14/2017
 */
public interface Converter<T, V> {

    T convertToEntity(V dto);

    V convertToDto(T entity);

    T update(T currentEntity, V dto);

}
