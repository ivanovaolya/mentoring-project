package com.mentoring.service.impl.jpa;

import java.util.List;

import com.mentoring.domain.entity.Address;
import com.mentoring.domain.entity.Phone;
import com.mentoring.domain.repository.jpa.AddressJpaDao;
import com.mentoring.domain.repository.jpa.PhoneJpaDao;
import com.mentoring.service.DataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author ivanovaolyaa
 * @version 9/13/2017
 */
@Service
@Qualifier("dataJpaService")
public class DataJpaServiceImpl implements DataService {

    @Autowired
    private AddressJpaDao addressJpaDao;

    @Autowired
    private PhoneJpaDao phoneJpaDao;

    @Override
    public List<Address> findAllAddresses() {
        return addressJpaDao.findAll();
    }

    @Override
    public List<Phone> findAllPhones() {
        return phoneJpaDao.findAll();
    }

}
