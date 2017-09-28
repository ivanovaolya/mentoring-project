package com.mentoring.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ivanovaolyaa
 * @version 9/26/2017
 */
@Entity
@Table(name = "cards")
@Getter
@Setter
@EqualsAndHashCode(exclude = "cardPk")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_pk")
    private Long cardPk;

    @Column(name = "card_number")
    private String cardNumber;

    private String cvd;

    @Column(name = "exp_month")
    private String expiredMonth;

    @Column(name = "exp_year")
    private String expiredYear;

}
