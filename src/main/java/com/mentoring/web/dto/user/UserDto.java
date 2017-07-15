package com.mentoring.web.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author ivanovaolyaa
 * @version 7/14/2017
 */
@Data
@Getter
@Setter
@ToString
public class UserDto extends RegistrationDto implements GenericUserDto {

    private String firstName;

    private String lastName;

    private Date birthDate;

}
