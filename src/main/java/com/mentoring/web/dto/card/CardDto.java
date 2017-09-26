package com.mentoring.web.dto.card;

import lombok.Data;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ivanovaolyaa
 * @version 9/26/2017
 */
@Data
public class CardDto {

    @Pattern(regexp = "^\\d{16}$")
    @JsonProperty("number")
    private String cardNumber;

    @Pattern(regexp = "^\\d{3}$")
    private String cvd;

    @Pattern(regexp = "^\\d{2}$")
    private String expiredMonth;

    @Pattern(regexp = "^\\d{2}$")
    private String expiredYear;

}
