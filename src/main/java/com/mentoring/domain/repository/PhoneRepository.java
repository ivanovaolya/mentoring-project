package com.mentoring.domain.repository;

import com.mentoring.domain.entity.Phone;
import com.mentoring.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface PhoneRepository extends CrudRepository<Phone, Long> {

    Phone findPhoneByUser(User user);

}
