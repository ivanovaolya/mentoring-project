package com.mentoring.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mentoring.domain.entity.Address;
import com.mentoring.domain.entity.Phone;
import com.mentoring.domain.entity.Role;
import com.mentoring.domain.entity.UserDetails;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.Pattern;

/**
 * @author ivanovaolyaa
 * @version 7/14/2017
 */
@Data
public class UserDto implements GenericUserDto {

    private String email;

    private String firstName;

    private String lastName;

    private List<Address> addresses;

    private List<Phone> phones;

    private Set<Role> roles;

    @Pattern(regexp = "^[A-Z]{2}\\d{6}$")
    @JsonProperty("passport")
    private String userDetailsPassport;  // for automatic mapping

    @Pattern(regexp = "^\\d{10}$")
    @JsonProperty("identificationNumber")
    private String userDetailsIdentificationNumber;

}
