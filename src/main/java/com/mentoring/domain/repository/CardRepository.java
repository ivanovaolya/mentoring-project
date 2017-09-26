package com.mentoring.domain.repository;

import com.mentoring.domain.entity.Card;

import org.springframework.data.repository.CrudRepository;

/**
 * @author ivanovaolyaa
 * @version 9/26/2017
 */
public interface CardRepository extends CrudRepository<Card, Long> {
}
