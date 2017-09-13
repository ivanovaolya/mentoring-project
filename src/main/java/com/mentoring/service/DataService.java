package com.mentoring.service;

import java.util.List;

import com.mentoring.domain.entity.Address;
import com.mentoring.domain.entity.Phone;

/**
 * @author ivanovaolyaa
 * @version 9/13/2017
 */
public interface DataService {

    List<Address> findAllAddresses();

    List<Phone> findAllPhones();

}
