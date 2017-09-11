package com.mentoring.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

/**
 * @author ivanovaolyaa
 * @version 9/11/2017
 */
@Embeddable
@Access(AccessType.FIELD)
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Passport {

    private String series;

    private String number;

}
