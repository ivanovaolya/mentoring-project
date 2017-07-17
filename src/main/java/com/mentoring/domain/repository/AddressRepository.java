package com.mentoring.domain.repository;

import com.mentoring.domain.entity.Address;
import com.mentoring.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {

    Address findAddressByUser(User user);

}
