package com.mentoring.web.dto.user;

import com.mentoring.domain.entity.Address;
import com.mentoring.domain.entity.Phone;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author ivanovaolyaa
 * @version 7/14/2017
 */
@Data
@Getter
@Setter
@ToString
public class UserDto implements GenericUserDto {

    private String email;

    private String firstName;

    private String lastName;

    private List<Address> addresses;

    private List<Phone> phones;

}
