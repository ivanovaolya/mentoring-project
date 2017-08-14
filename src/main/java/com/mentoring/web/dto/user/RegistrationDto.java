package com.mentoring.web.dto.user;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author ivanovaolyaa
 * @version 7/13/2017
 */
@Data
@Getter
public class RegistrationDto implements GenericUserDto {

    @NotNull
    @Pattern(regexp = "(?i)^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
    private String email;

    @NotNull
    @Size(min = 6, max = 30)
    private String password;

    @NotNull
    @Size(min = 6, max = 30)
    private String confirmPassword;

    @NotNull
    private String role;

}
