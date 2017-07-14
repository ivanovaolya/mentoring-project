package com.mentoring.domain.repository;

import com.mentoring.domain.entity.User;

/**
 * @author ivanovaolyaa
 * @version 7/13/2017
 */
public interface UserRepository extends GenericCrudRepository<User> {

    User findByEmail(String email);

}
