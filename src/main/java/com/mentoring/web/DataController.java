package com.mentoring.web;

import java.util.List;

import com.mentoring.domain.entity.Address;
import com.mentoring.domain.entity.Phone;
import com.mentoring.service.DataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ivanovaolyaa
 * @version 9/13/2017
 */
@RestController
public class DataController {

    @Autowired
    @Qualifier("dataJpaService")
    private DataService dataService;

    @GetMapping()
    @RequestMapping("/addresses")
    public List<Address> getAllAddresses() {
        return dataService.findAllAddresses();
    }

    @GetMapping()
    @RequestMapping("/phones")
    public List<Phone> getAllPhones() {
        return dataService.findAllPhones();
    }

}
